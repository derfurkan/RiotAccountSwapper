package de.furkan.accountswapper.frames;

import de.furkan.accountswapper.Main;
import de.furkan.componentutil.components.ButtonComponent;
import de.furkan.componentutil.components.ComboboxComponent;
import de.furkan.componentutil.components.ImageComponent;
import de.furkan.componentutil.components.TextComponent;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;
import org.apache.commons.io.FileUtils;

public class MainFrame extends JFrame {

  private final JPanel panel = new JPanel(null);
  ButtonComponent swapButton, saveAccount;
  ComboboxComponent accountSelection;
  ButtonComponent deleteAccount;

  public MainFrame() {
      this.setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
    this.setTitle("Riot Account Swapper");
    this.setSize(700, 430);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setVisible(true);
    this.setContentPane(panel);
    this.setLocationRelativeTo(null);
    ImageComponent riotLogo =
        new ImageComponent(
            getClass().getResource("/images/riotLogo.png"),
            0,
            30,
            330,
            120,
            Image.SCALE_SMOOTH,
            this.getSize(),
            panel);

    riotLogo.setX((this.getWidth() / 2) - (riotLogo.getWidth() / 2) - 19);

    riotLogo.create(false, false, true, true);

    createSelectionBox();
    createButtons();
  }

  private void createButtons() {
    swapButton =
        new ButtonComponent(
            accountSelection.getComponent().getSelectedItem() == null
                    || !accountSelection.getComponent().isEnabled()
                ? "Nothing selected"
                : "Swap to " + accountSelection.getComponent().getSelectedItem().toString(),
            0,
            235,
            30,
            190,
            Font.SANS_SERIF,
            15,
            Font.PLAIN,
            Color.BLACK,
            Color.WHITE,
            this.getSize(),
            panel);

    swapButton.create(true, false);
    swapButton
        .getComponent()
        .addActionListener(
            e -> {
              if (accountSelection.getComponent().getSelectedItem() == null
                  || !accountSelection.getComponent().isEnabled()) {
                return;
              }
              swapButton.getComponent().setEnabled(false);
              String olderButtonText = swapButton.getComponent().getText();
              swapButton.setButtonText("Swapping...");
              Main.printConsole(
                  "Swapping to account "
                      + accountSelection.getComponent().getSelectedItem().toString());

              Main.riotClientUtil.loadAccountDataFromKey(
                  Main.accountData.get(
                      accountSelection.getComponent().getSelectedItem().toString()));
              Main.printConsole(
                  "Swapped to account "
                      + accountSelection.getComponent().getSelectedItem().toString());
              swapButton.getComponent().setEnabled(true);
              swapButton.setButtonText(olderButtonText);
              JOptionPane.showMessageDialog(
                  this,
                  "The account has been swapped successfully to "
                      + accountSelection.getComponent().getSelectedItem().toString()
                      + "!",
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);
            });
    if (accountSelection.getComponent().getSelectedItem() == null
        || !accountSelection.getComponent().isEnabled()) {
      swapButton.getComponent().setEnabled(false);
    }
    saveAccount =
        new ButtonComponent(
            "Save current Account",
            0,
            290,
            30,
            190,
            Font.SANS_SERIF,
            15,
            Font.PLAIN,
            Color.BLACK,
            Color.WHITE,
            this.getSize(),
            panel);

    saveAccount.create(true, false);

    saveAccount
        .getComponent()
        .addActionListener(
            e -> {
              String name =
                  JOptionPane.showInputDialog(
                      this,
                      "Enter the name of the account",
                      "Save Account",
                      JOptionPane.PLAIN_MESSAGE);
              if (name == null) {
                return;
              }
              if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "The name of the account can't be empty!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
              }
              if (Main.accountData.containsKey(name)) {
                JOptionPane.showMessageDialog(
                    this,
                    "The name of the account is already in use!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
              }
              String randomKey = String.valueOf(ThreadLocalRandom.current().nextInt(0, 10000));
              if (Main.accountData.containsValue(randomKey)) {
                JOptionPane.showMessageDialog(
                    this,
                    "An error occurred while saving the account!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
              }

              Main.accountData.put(name, randomKey);
              Main.riotClientUtil.saveCurrentAccountToKey(randomKey);
              if (!accountSelection.getComponent().isEnabled()) {
                accountSelection.getComponent().removeAllItems();
              }
              accountSelection.getComponent().setEnabled(true);
              accountSelection.getComponent().addItem(name);
              accountSelection.getComponent().setSelectedItem(name);
              Main.updateConfigFile();
              JOptionPane.showMessageDialog(
                  this,
                  "The account has been saved successfully!",
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);
            });

    // Create delete selected account button
    deleteAccount =
        new ButtonComponent(
            "Delete selected Account",
            0,
            330,
            30,
            190,
            Font.SANS_SERIF,
            15,
            Font.PLAIN,
            Color.BLACK,
            Color.WHITE,
            this.getSize(),
            panel);

    deleteAccount.create(true, false);

    if (!accountSelection.getComponent().isEnabled()) {
      deleteAccount.getComponent().setEnabled(false);
    }

    deleteAccount
        .getComponent()
        .addActionListener(
            e -> {
              Main.printConsole(
                  "Deleting account: "
                      + accountSelection.getComponent().getSelectedItem().toString());
              if (accountSelection.getComponent().getSelectedItem() == null
                  || !accountSelection.getComponent().isEnabled()) {
                Main.printConsole("No account selected!");
                return;
              }
              System.out.println(Main.configFile.getParentFile());
              System.out.println(
                  Main.accountData.get(
                      accountSelection.getComponent().getSelectedItem().toString()));
              try {
                FileUtils.deleteDirectory(
                    new File(
                        Main.configFile.getParentFile()
                            + "\\"
                            + Main.accountData.get(
                                accountSelection.getComponent().getSelectedItem().toString())));
              } catch (IOException ex) {
                  JOptionPane.showMessageDialog(
                          this,
                          "An error occurred while deleting the account!",
                          "Error",
                          JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(ex);
              }

              Main.accountData.remove(accountSelection.getComponent().getSelectedItem().toString());

              accountSelection
                  .getComponent()
                  .removeItem(accountSelection.getComponent().getSelectedItem());
              if (accountSelection.getComponent().getItemCount() == 0) {
                deleteAccount.getComponent().setEnabled(false);
                accountSelection.getComponent().setEnabled(false);
                accountSelection.getComponent().addItem("No accounts found");
              }
              Main.updateConfigFile();
              Main.printConsole("Account deleted!");
            });
    // Add logout button in right corner
    ButtonComponent logoutButton =
        new ButtonComponent(
            "Logout",
            10,
            350,
            30,
            120,
            Font.SANS_SERIF,
            15,
            Font.PLAIN,
            Color.BLACK,
            Color.WHITE,
            this.getSize(),
            panel);
    logoutButton.create(false, false);

    logoutButton.getComponent().addActionListener(e -> {
        swapButton.getComponent().setEnabled(false);
        String olderButtonText = swapButton.getComponent().getText();
        swapButton.setButtonText("Logging out...");
        Main.riotClientUtil.killRiotClient();
        Main.riotClientUtil.clearRiotData();
        Main.riotClientUtil.startRiotClient();
        swapButton.setButtonText(olderButtonText);
        swapButton.getComponent().setEnabled(true);
    });

    // Add how to use button in right corner
    ButtonComponent howToUseButton =
        new ButtonComponent(
            "How to use",
            10,
            315,
            30,
            120,
            Font.SANS_SERIF,
            15,
            Font.PLAIN,
            Color.BLACK,
            Color.WHITE,
            this.getSize(),
            panel);

    howToUseButton.create(false, false);

    howToUseButton
        .getComponent()
        .addActionListener(
            e -> {
              JOptionPane.showMessageDialog(
                  this,
                  "To save an account you need to be already logged in to an account in the Riot Client.\nAfter that you can click 'Save current Account' and pick a name for the Account to save it.\nIf you want to save another account you need to logout and login to the other account again.\nTo do that do NOT use the 'logout' function from the Riot Client instead use the 'Logout' button in the right corner.\nThe Riot Client will restart and you can login and save another account from now on.\nDo this with all accounts you want to save in this application\n\nUsing the logout function from the Client itself can cause a regeneration of the login token\nand some of your saved accounts may not work when swapping.\n\nAlso make sure while logging in having the 'Stay signed in' checkbox checked.",
                  "How to use",
                  JOptionPane.INFORMATION_MESSAGE);
            });
  }

  private void createSelectionBox() {

    TextComponent comboBoxTitle =
        new TextComponent(
            "Select a account",
            7,
            180,
            new Font(Font.SANS_SERIF, Font.PLAIN, 15),
            Color.BLACK,
            this.getSize(),
            panel);

    // comboBoxTitle.setX((this.getWidth() / 2) - (120 / 2) + 6);

    comboBoxTitle.create(true, false);

    accountSelection =
        new ComboboxComponent(
            0,
            200,
            25,
            190,
            Font.SANS_SERIF,
            15,
            Font.PLAIN,
            Color.BLACK,
            Color.WHITE,
            this.getSize(),
            panel);

    if (accountSelection.getComponent().getItemCount() == 0) {
      accountSelection
          .getComponent()
          .setRenderer(
              new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                  super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                  setHorizontalAlignment(CENTER);
                  return this;
                }
              });
    }

    Main.accountData.forEach((name, password) -> accountSelection.addElement(name));
    if (accountSelection.getComponent().getItemCount() == 0) {
      accountSelection.addElement("No accounts found");
      accountSelection.getComponent().setEnabled(false);
    }

    accountSelection.create(true, false);
    accountSelection.getComponent().revalidate();
    accountSelection.getComponent().repaint();
    accountSelection
        .getComponent()
        .addActionListener(
            e -> {
              if (accountSelection.getComponent().getSelectedItem() == null
                  || !accountSelection.getComponent().isEnabled()) {
                swapButton.setButtonText("Nothing selected");
                swapButton.getComponent().setEnabled(false);
              } else if (accountSelection.getComponent().isEnabled()) {
                deleteAccount.getComponent().setEnabled(true);
                swapButton.setButtonText(
                    "Swap to " + accountSelection.getComponent().getSelectedItem());
                swapButton.getComponent().setEnabled(true);
              }
            });
  }
}
