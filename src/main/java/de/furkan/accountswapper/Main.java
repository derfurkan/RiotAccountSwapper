package de.furkan.accountswapper;

import com.google.gson.JsonObject;
import de.furkan.accountswapper.frames.MainFrame;
import de.furkan.accountswapper.util.RiotClientUtil;
import org.apache.commons.io.FileUtils;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;import java.util.Date;
import java.util.HashMap;import java.util.List;
import javax.swing.*;

public class Main {

  public static final String VERSION = "1.2";

  public static final File configFile =
      new File(System.getenv("LOCALAPPDATA") + "/RiotAccountSwapper/config.json");
  public static RiotClientUtil riotClientUtil;

  public static HashMap<String, String> accountData = new HashMap<>();

  public static boolean isOperating = false;

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException
        | InstantiationException
        | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      throw new RuntimeException(e);
    }
    riotClientUtil =
        new RiotClientUtil(new File(System.getenv("LOCALAPPDATA") + "/Riot Games/Riot Client"));
    if (!configFile.exists()) {
      printConsole("Config folder does not exist, creating...");
      try {
        configFile.getParentFile().mkdirs();
        configFile.createNewFile();
      } catch (IOException e) {
        JOptionPane.showMessageDialog(
            null,
            "Could not create config file!\n\n" + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        System.exit(0);
        throw new RuntimeException(e);
      }

      printConsole("Config folder created!");
      printConsole("Writing default config...");

      JsonObject defaultConfig = new JsonObject();

      JsonObject accountDataJson = new JsonObject();
      for (String key : accountData.keySet()) {
        accountDataJson.addProperty(key, accountData.get(key));
      }

      defaultConfig.add("accountData", accountDataJson);

      try {
        FileWriter writer = new FileWriter(configFile);
        writer.write(defaultConfig.toString());
        writer.flush();
        writer.close();
      } catch (IOException e) {
        JOptionPane.showMessageDialog(
            null,
            "Could not write default config!\n\n" + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        System.exit(0);
        throw new RuntimeException(e);
      }

      printConsole("Default config written!");
      printConsole("Ready to use!");
    } else {
      printConsole("Reading config...");
      String json;
      try {
        FileReader fileReader = new FileReader(configFile);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
          stringBuilder.append(line);
          stringBuilder.append(System.lineSeparator());
          line = bufferedReader.readLine();
        }
        json = stringBuilder.toString();
        printConsole("Config read!");
      } catch (IOException e) {
        JOptionPane.showMessageDialog(
            null,
            "Could not read config file!\n\n" + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        System.exit(0);
        throw new RuntimeException(e);
      }

      JsonObject jsonObject = riotClientUtil.gson.fromJson(json, JsonObject.class);
      JsonObject accountDataJson = jsonObject.get("accountData").getAsJsonObject();
      for (String key : accountDataJson.keySet()) {
        accountData.put(key, accountDataJson.get(key).getAsString());
      }

      printConsole("Ready to use!");
    }
    new LoginWatcher();
    new MainFrame();
  }

  public static void updateConfigFile() {
    printConsole("Updating config file...");
    configFile.delete();
    try {
      configFile.createNewFile();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(
          null,
          "Could not update config file!\n\n" + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
      System.exit(0);
      throw new RuntimeException(e);
    }
    JsonObject jsonObject = new JsonObject();
    JsonObject accountDataJson = new JsonObject();
    for (String key : accountData.keySet()) {
      accountDataJson.addProperty(key, accountData.get(key));
    }
    jsonObject.add("accountData", accountDataJson);
    String json = riotClientUtil.gson.toJson(jsonObject);

    try {
      FileWriter writer = new FileWriter(configFile);
      writer.write(json);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(
          null,
          "Could not update config file!\n\n" + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
      System.exit(0);
      throw new RuntimeException(e);
    }
    printConsole("Config file updated!");
  }

  public static void printConsole(String message) {
    System.out.println(
        "[" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()) + "] " + message);
  }
}

class LoginWatcher implements Runnable {

  public LoginWatcher() {
    new Thread(this).start();
  }

  @Override
  public void run() {

    while (true) {
      try {
        Thread.sleep(1000);

        if(Main.isOperating) continue;
        if(Main.riotClientUtil.isLoggedIn()) {
          List<String> list =
              FileUtils.readLines(
                  new File(
                      Main.riotClientUtil.riotClientDataPath
                          + "\\Data\\RiotGamesPrivateSettings.yaml"));
          FileUtils.deleteQuietly(
              new File(
                  Main.riotClientUtil.riotClientDataPath
                      + "\\Data\\RiotGamesPrivateSettings.yaml"));
          List<String> newList = new ArrayList<>();
            for(String line : list) {
            if (line.contains("region:")) {
              line = "        region: \""+MainFrame.regionSelection.getSelected()+"\"";
              }
              newList.add(line);
            }
          new File(Main.riotClientUtil.riotClientDataPath + "\\Data\\RiotGamesPrivateSettings.yaml")
              .createNewFile();
            FileUtils.writeLines(new File(Main.riotClientUtil.riotClientDataPath + "\\Data\\RiotGamesPrivateSettings.yaml"), newList);
        }
      } catch (Exception e) {

        JOptionPane.showMessageDialog(
            null,
            "Could not watch for login!\n\n" + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        System.exit(0);
      }
    }

  }
}
