package de.furkan.accountswapper.util;

import com.google.gson.Gson;
import de.furkan.accountswapper.Main;
import java.io.*;
import java.nio.charset.Charset;
import javax.swing.*;
import org.apache.commons.io.FileUtils;

public class RiotClientUtil {

  public final Gson gson = new Gson();

  public final File riotClientDataPath;

  public RiotClientUtil(File riotClientDataPath) {
    this.riotClientDataPath = riotClientDataPath;
    if (!riotClientDataPath.exists()) {
      Main.printConsole("Riot Client Data does not exist!");
      System.exit(0);
    }
  }

  public boolean isLoggedIn() {
    try {
      return new File(riotClientDataPath + "\\Data\\RiotGamesPrivateSettings.yaml").exists()
          && FileUtils.readLines(
                      new File(riotClientDataPath + "\\Data\\RiotGamesPrivateSettings.yaml"),
                      Charset.defaultCharset())
                  .size()
              > 16;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void clearRiotData() {
    Main.printConsole("Clearing Riot Client Data...");
    try {
      FileUtils.cleanDirectory(riotClientDataPath);
      Thread.sleep(100);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(
          null,
          "Could not clear Riot Client Data!\n\n" + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
      throw new RuntimeException(e);
    }
    Main.printConsole("Cleared Riot Client Data!");
  }

  public void killRiotClient() {
    Main.printConsole("Killing Riot Client...");
    try {
      Runtime.getRuntime().exec("taskkill /F /IM \"RiotClientServices.exe\"");
      Runtime.getRuntime().exec("taskkill /F /IM \"RiotClientUx.exe\"");
      Runtime.getRuntime().exec("taskkill /F /IM \"RiotClientCrashHandler.exe\"");
      Runtime.getRuntime().exec("taskkill /F /IM \"RiotClientInstaller.exe\"");
      Runtime.getRuntime().exec("taskkill /F /IM \"RiotClientPatcher.exe\"");
      Runtime.getRuntime().exec("taskkill /F /IM \"RiotClientUxRender.exe\"");
      Runtime.getRuntime().exec("taskkill /F /IM \"RiotClientUxServices.exe\"");
      Runtime.getRuntime().exec("taskkill /F /IM \"RiotClientService.exe\"");
      Runtime.getRuntime().exec("taskkill /F /IM \"RiotClient.exe\"");
    } catch (IOException e) {
      JOptionPane.showMessageDialog(
          null,
          "Could not kill Riot Client!\n\n" + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
      throw new RuntimeException(e);
    }
    Main.printConsole("Killed Riot Client!");
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void startRiotClient() {
    Main.printConsole("Starting Riot Client...");
    File riotClientPath = new File("C:\\Riot Games\\Riot Client\\RiotClientServices.exe");
    ProcessBuilder pb = new ProcessBuilder(riotClientPath.getAbsolutePath());
    try {
      Thread.sleep(200);
      pb.start();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(
          null,
          "Could not start Riot Client!\n\n" + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
      throw new RuntimeException(e);
    }
    Main.printConsole("Started Riot Client!");
  }

  public void saveCurrentAccountToKey(String key) {
    Main.printConsole("Saving current account to key " + key + "...");
    // Create folder in folder where config is stored
    File accountFolder = new File(Main.configFile.getParentFile().getAbsolutePath() + "\\" + key);
    if (!accountFolder.exists()) {
      accountFolder.mkdirs();
    } else {
      JOptionPane.showMessageDialog(
          null, "Key already exists!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    // Copy files and folders from Riot Client Data to account folder
    try {
      for (File file : riotClientDataPath.listFiles()) {
        FileUtils.copyToDirectory(file, accountFolder);
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(
          null,
          "Could not save current account!\n\n" + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
      throw new RuntimeException(e);
    }
    Main.printConsole("Saved current account to key " + key + "!");
  }

  public void loadAccountDataFromKey(String key) {
    Main.printConsole("Loading account data from key " + key + "...");
    try {
      for (File file :
          new File(Main.configFile.getParentFile().getAbsolutePath() + "\\" + key).listFiles()) {
        FileUtils.copyToDirectory(file, riotClientDataPath);
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(
          null,
          "Could not load account data!\n\n" + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
      throw new RuntimeException(e);
    }
    Main.printConsole("Loaded account data from key " + key + "!");
  }
}
