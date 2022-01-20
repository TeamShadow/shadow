package shadow;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

public class RegistryAccess {

  public static String readString(WinReg.HKEY topLevel, String key, String value) {
    return Advapi32Util.registryGetStringValue(topLevel, key, value);
  }

  public static int readInt(WinReg.HKEY topLevel, String key, String value) {
    return Advapi32Util.registryGetIntValue(topLevel, key, value);
  }

  public static boolean keyExists(WinReg.HKEY topLevel, String key) {
    return Advapi32Util.registryKeyExists(topLevel, key);
  }

  public static String[] getKeys(WinReg.HKEY topLevel, String key) {
    return Advapi32Util.registryGetKeys(topLevel, key);
  }
/* Examples
    // Read a string
    String productName = Advapi32Util.registryGetStringValue(
        WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion", "ProductName");
    System.out.printf("Product Name: %s\n", productName);

    // Read an int (& 0xFFFFFFFFL for large unsigned int)
    int timeout = Advapi32Util.registryGetIntValue(
        WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Windows", "ShutdownWarningDialogTimeout");
    System.out.printf("Shutdown Warning Dialog Timeout: %d (%d as unsigned long)\n", timeout, timeout & 0xFFFFFFFFL);

    // Create a key and write a string
    Advapi32Util.registryCreateKey(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\StackOverflow");
    Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\StackOverflow", "url", "http://stackoverflow.com/a/6287763/277307");

    // Delete a key
    Advapi32Util.registryDeleteKey(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\StackOverflow");
  */
}