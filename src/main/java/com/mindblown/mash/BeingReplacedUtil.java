package com.mindblown.mash;

/**
 * I HAVE COMMENTED ALL THIS OUT. THIS IS THE OLD UTIL THAT USED TO BE USED BEFORE I USED A LIBRARIZED UTIL. 
 * I'M KEEPING THIS HERE FOR REFERENCE
 **/


//package com.mindblown.masterprompt;
//
//
//import java.awt.Component;
//import java.awt.Desktop;
//import java.awt.Dialog.ModalityType;
//import java.awt.Image;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Scanner;
//import javax.imageio.ImageIO;
//import javax.swing.JDialog;
//import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;
//import javax.swing.filechooser.FileFilter;
//import javax.swing.filechooser.FileNameExtensionFilter;
//
///*
//* To change this license header, choose License Headers in Project Properties.
//* To change this template file, choose Tools | Templates
//* and open the template in the editor.
//*/
//
///**
// *
// * @author beamj
// */
//public class BeingReplacedUtil {
//    
//    public static int filesOnly = JFileChooser.FILES_ONLY;
//    public static int dirsOnly = JFileChooser.DIRECTORIES_ONLY;
//    public static int filesAndDirs = JFileChooser.FILES_AND_DIRECTORIES;
//    
//    public static String currentWriterFileName = "";
//    
//    public static PrintWriter getWriter(String origName, String fileType){
//        String name = origName.substring(0);
//        PrintWriter writer = null;
//        boolean go = true;
//        while(go){
//            try {
//                writer = new PrintWriter(name + fileType);
//                go = false;
//            } catch (FileNotFoundException ex) {
//                name += "0";
//            }
//        }
//        currentWriterFileName = name + fileType;
//        return writer;
//    }
//    
//    public static <T> ArrayList<T> toList(T[] a){
//        ArrayList<T> list = new ArrayList<>();
//        for(int i = 0; i < a.length; i++){
//            list.add(a[i]);
//        }
//        return list;
//    }
//    
//    public static <T> Object[] toArray(ArrayList<T> list){
//        return list.toArray();
//    }
//    
//    public static int[] toIntArray(ArrayList<Integer> list){
//        int[] a = new int[list.size()];
//        for(int i = 0; i < a.length; i++){
//            a[i] = list.get(i);
//        }
//        return a;
//    }
//    
//    public static void runBatch(File batch) throws Exception {
//        Runtime rt = Runtime.getRuntime();
//        rt.exec("cmd /c start " + batch);
//    }
//    
//    public static File getFile(int selectionMode, String title) {
//        return getFile(selectionMode, title, null);
//    }
//    
//    public static File getFile(int selectionMode, String title, File f) {
//        return getFile(selectionMode, title, null, f);
//    }
//    
//    public static File getFile(int selectionMode, String title, FileFilter filter, File f) {
//        File file = null;
//        JFileChooser chooser = new JFileChooser();
//        if(filter != null){
//            chooser.setFileFilter(filter);
//        }
//        chooser.setFileSelectionMode(selectionMode);
//        chooser.setDialogTitle(title);
//        if(f != null){
//            chooser.setSelectedFile(f);
//        }
//        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//            file = chooser.getSelectedFile();
//        }
//        return file;
//    }
//    
//    public static boolean isImg(File img){
//        try {
//            Image image = ImageIO.read(img);
//            return image != null;
//        } catch(Exception e) {
//            return false;
//        }
//    }
//    
//    public static int objectExists(Object[] o, Object obj){
//        for(int i = 0; i < o.length; i++){
//            if(o[i] == obj){
//                return i;
//            }
//        }
//        return -1;
//    }
//    
//    public static int objectExists(Object[] o, Object obj, boolean rel){
//        for(int i = 0; i < o.length; i++){
//            if(rel){
//                if(o[i].equals(obj)){
//                    return i;
//                }
//            } else {
//                if(o[i] == obj){
//                    return i;
//                }
//            }
//        }
//        return -1;
//    }
//    
//    public static String nextAlpha(String text){
//        return nextAlpha(text, 0);
//    }
//    
//    public static String nextAlpha(String text, int start){
//        for(int i = start ; i < text.length(); i++){
//            char c = text.charAt(i);
//            if(Character.isAlphabetic(c)){
//                return c + "";
//            }
//        }
//        return null;
//    }
//    
//    public static String getText(File file){
//        if(!file.isFile()){
//            return "";
//        }
//        String text = "";
//        try(Scanner in = new Scanner(file)){
//            while(in.hasNextLine()){
//                text += in.nextLine();
//                text += "\n";
//            }
//        } catch (FileNotFoundException ex) {
//            return "";
//        }
//        return text;
//    }
//    
//    public static String removeLines(String s){
//        String string = Util.strip(s);
//        String text = "";
//        for(int i = 0; i < string.length(); i++){
//            String str = string.substring(i, i+1);
//            if(!(Util.isBlank(str) && !str.equals(" "))){
//                text += str;
//            }
//        }
//        return text;
//    }
//    
//    public static <T> void print(ArrayList<T> list){
//        for(T o : list){
//            System.out.println(o);
//        }
//    }
//    
//    public static <T extends Comparable> int binaryIndexOf(ArrayList<? extends Comparable<? super T>> list, T key){
//        return Collections.binarySearch(list, key);
//    }
//    
//    public static <T extends Comparable> boolean binaryHas(ArrayList<? extends Comparable<? super T>> list, T key){
//        return binaryIndexOf(list, key) > -1;
//    }
//    
//    public static <T extends Comparable<? super T>> boolean binaryAdd(ArrayList<T> list, T key){
//        int ind = Util.binaryIndexOf(list, key);
//        if(ind >= 0){
//            return false;
//        } else {
//            ind = -1 * (ind + 1);
//            list.add(ind, key);
//            return true;
//        }
//    }
//    
//    public static <T extends Comparable<? super T>> boolean binaryAddAll(ArrayList<T> list, ArrayList<T> key){
//        boolean ret = true;
//        for(T t : key){
//            int ind = Util.binaryIndexOf(list, t);
//            if(ind >= 0){
//                ret = false;
//            } else {
//                ind = -1 * (ind + 1);
//                list.add(ind, t);
//            }
//        }
//        return ret;
//    }
//    
//    public static <T extends Comparable> boolean binaryRemove(ArrayList<? extends Comparable<? super T>> list, T key){
//        int ind = Util.binaryIndexOf(list, key);
//        if(ind >= 0){
//            list.remove(ind);
//            return true;
//        } else {
//            return false;
//        }
//    }
//    
//    public static int binaryIndexOf(Object[] list, Object key){
//        return Arrays.binarySearch(list, key);
//    }
//    
//    public static boolean binaryHas(Object[] list, Object key){
//        return binaryIndexOf(list, key) > -1;
//    }
//    
//    public static boolean binaryHas(Object[] list, Object[] keys){
//        for(Object key : keys){
//            if(binaryHas(list, key)){
//                return true;
//            }
//        }
//        return false;
//    }
//    
//    public static boolean binaryPut(Object[] list, Object key){
//        int ind = Util.binaryIndexOf(list, key);
//        if(ind >= 0){
//            return false;
//        } else {
//            ind = -1 * (ind + 1);
//            if(ind >= list.length){
//                return false;
//            } else {
//                list[ind] = key;
//                return true;
//            }
//        }
//    }
//    
//    public static boolean isBlank(String str){
//        return strip(str).isEmpty();
//    }
//    
//    public static String strip(String s){
//        return s.trim();
//    }
//    
//    public static JDialog showNonBlocking(Object obj, String title, boolean resizable){
//        return show(obj, title, JDialog.DISPOSE_ON_CLOSE, JDialog.ModalityType.MODELESS, resizable);
//    }
//    
//    public static JDialog showBlocking(Object obj, String title, boolean resizable){
//        return show(obj, title, JDialog.DISPOSE_ON_CLOSE, JDialog.DEFAULT_MODALITY_TYPE, resizable);
//    }
//    
//    /**
//     * Shows normal with non resizable, blocking, and dispose on close.
//     * @param obj
//     * @param title
//     * @return 
//     */
//    public static JDialog showNorm(Object obj, String title){
//        return show(obj, title, false, true, true);
//    }
//    
//    public static JDialog show(Object obj, String title, boolean resizable, boolean block, boolean disposeOnClose){
//        int close;
//        if(disposeOnClose){
//            close = JDialog.DISPOSE_ON_CLOSE;
//        } else {
//            close = JDialog.DO_NOTHING_ON_CLOSE;
//        }
//        ModalityType modal;
//        if(block){
//            modal = JDialog.DEFAULT_MODALITY_TYPE;
//        } else {
//            modal = JDialog.ModalityType.MODELESS;
//        }
//        return show(obj, title, close, modal, resizable);
//    }
//    
//    public static JDialog show(Object obj, String title, int close, ModalityType modalityType, boolean resizable){
//        JOptionPane pane = new JOptionPane(obj, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
//        JDialog dial = new JDialog();
//        dial.setTitle(title);
//        dial.setContentPane(pane);
//        dial.setResizable(resizable);
//        dial.setModalityType(modalityType);
//        dial.setDefaultCloseOperation(close);
//        dial.pack();
//        return dial;
//    }
//    
//    public static String input(String message, String title){
//        return input(null, message, title);
//    }
//    
//    public static String input(Component parent, String message, String title){
//        return JOptionPane.showInputDialog(parent, message, title, JOptionPane.PLAIN_MESSAGE);
//    }
//    
//    public static int confirm(String message, String title){
//        return confirm(null, message, title);
//    }
//    
//    public static int confirm(Component parent, String message, String title){
//        return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//    }
//    
//    public static boolean yesNo(String message, String title){
//        return yesNo(null, message, title);
//    }
//    
//    public static boolean yesNo(Component parent, String message, String title){
//        return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION;
//    }
//    
//    public static boolean open(File file){
//        try {
//            Desktop.getDesktop().open(file);
//        } catch (Exception e){
//            try {
//                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", file.toString()});
//            } catch(Exception ex){
//                return false;
//            }
//        }
//        return true;
//    }
//    
//    public static boolean openDir(File file){
//        try {
//            String open;
//            if(file.isDirectory()){
//                open = file.toString();
//            } else {
//                open = file.getParent();
//            }
//            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", open});
//        } catch(Exception ex){
//            return false;
//        }
//        return true;
//    }
//    
//    public static boolean open(String url){
//        try {
//            Desktop.getDesktop().browse(new URI(url));
//        } catch (Exception e){
//            try {
//                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
//            } catch(Exception ex){
//                return false;
//            }
//        }
//        return true;
//    }
//    
//    public static void message(Component comp, Object obj, String title){
//        JOptionPane.showMessageDialog(comp, obj, title, JOptionPane.PLAIN_MESSAGE);
//    }
//    
//    public static boolean shortcut(String lnkName, String relFilePath, String shortcutParent, String errorMessage, String[] pastVers){
//        for (int i = 0; i < pastVers.length; i++) {
//            File file = new File(shortcutParent, pastVers[i]);
//            if (file.isFile()) {
//                file.delete();
//            }
//        }
//        String lnk = lnkName + ".lnk";
//        if (new File(shortcutParent, lnk).exists()) {
//            return true;
//        }
//        File batch = new File("ShortcutMaker.bat");
//        PrintWriter writer = null;
//        boolean ret = true;
//        try {
//            writer = new PrintWriter(batch);
//            writer.println("@echo off");
//            writer.println("set startupPath=\"" + shortcutParent + "\"\n"
//                    + "set exePath=\"%CD%\n"
//                    + "cd %startupPath%\n"
//                    + "echo Set oWS = WScript.CreateObject(\"WScript.Shell\") > %startupPath%\\CreateShortcut.vbs\n"
//                    + "echo sLinkFile = \"" + lnk + "\" >> %startupPath%\\CreateShortcut.vbs\n"
//                            + "echo Set oLink = oWS.CreateShortcut(sLinkFile) >> %startupPath%\\CreateShortcut.vbs\n"
//                            + "echo oLink.TargetPath = %exePath%\\" + relFilePath + "\" >> %startupPath%\\CreateShortcut.vbs\n"
//                                    + "echo oLink.WorkingDirectory = %startupPath%\" >> %startupPath%\\CreateShortcut.vbs\n"
//                                    + "echo oLink.Description = \"" + lnkName + "\" >> %startupPath%\\CreateShortcut.vbs\n"
//                                            + "echo oLink.Save >> %startupPath%\\CreateShortcut.vbs\n"
//                                            + "cd %startupPath%\n"
//                                            + "C:\\Windows\\System32\\cscript.exe %startupPath%\\CreateShortcut.vbs\n"
//                                            + "del CreateShortcut.vbs\n"
//                                            + "echo %CD%\n"
//                                            + "start " + lnk +"\n"
//                                                    + "cd %exePath%\n"
//                                                    +"(goto) 2>nul & del \"%~f0\" & exit /b");
//            Desktop.getDesktop().open(batch);
//        }
//        catch (Exception e) {
//            boolean fail = false;
//            if (batch.exists()) {
//                try {
//                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", batch.toString()});
//                }
//                catch(Exception ex) {
//                    fail = true;
//                    ret = true;
//                    message(null, errorMessage, "Error!");
//                }
//            } else {
//                fail = true;
//                ret = true;
//                message(null, errorMessage, "Error!");
//            }
//            if (fail) {
//                Util.delete(batch);
//            }
//        } finally {
//            writer.close();
//        }
//        return ret;
//    }
//    
//    public static void startupShortcut(String lnkName, String relFilePath, String... pastVers) {
//        String shortcutParent = "C:\\users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
//        shortcut(lnkName, relFilePath, shortcutParent, "Unable to set to open when computer starts!", pastVers);
//    }
//    
//    public static void sendToShortcut(String lnkName, String relFilePath, String... pastVers) {
//        String shortcutParent = "C:\\users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Microsoft\\Windows\\SendTo";
//        shortcut(lnkName, relFilePath, shortcutParent, "Unable to SendTo Shortcut!", pastVers);
//    }
//    
//    public static FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
//    public static FileNameExtensionFilter vidFilter = new FileNameExtensionFilter("Videos", new String[]{"avi", "riff", "mpg",
//        "vob", "mp4", "m2ts", "mov", "3gp", "mkv"});
//    
//    public static FileFilter filesOnlyFilter = new FileFilter() {
//        @Override
//        public boolean accept(File f) {
//            return f.isFile();
//        }
//
//        @Override
//        public String getDescription() {
//            return "Files";
//        }
//    };
//    
//    public static File getImgFile(String title) {
//        return getFile(JFileChooser.FILES_ONLY, title, imgFilter, null);
//    }
//    
//    public static ArrayList<File> getImgFiles(File dir, boolean noSub){
//        return getFilesOfType(dir, noSub, imgFilter);
//    }
//    
//    public static ArrayList<File> getMovies(File dir, boolean noSub){
//        return getFilesOfType(dir, noSub, vidFilter);
//    }
//    
//    public static ArrayList<File> getFilesOfType(File dir, boolean noSub, FileFilter... filters){
//        ArrayList<File> files = new ArrayList<>();
//        if(!dir.isDirectory()){
//            return files;
//        }
//        
//        if(filters.length == 0){
//            return files;
//        }
//        
//        ArrayList<File> dirsToCheck = new ArrayList<>();
//        dirsToCheck.add(dir);
//        
//        while(!dirsToCheck.isEmpty()){
//            File file = dirsToCheck.remove(0);
//            File[] fs = file.listFiles();
//            for(int i = 0; i < fs.length; i++){
//                File f = fs[i];
//                if(!noSub && f.isDirectory()){
//                    dirsToCheck.add(f);
//                } else {
//                    for(int j = 0; j < filters.length; j++){
//                        FileFilter filter = filters[j];
//                        if(filter.accept(f)){
//                            files.add(f);
//                        }
//                    }
//                }
//            }
//        }
//        return files;
//    }
//    
//    public static double removeDecimal(double num, int maxDec){
//        String rep = Double.toString(num);
//        if(rep.length() - (rep.lastIndexOf(".") + 1) <= maxDec){
//            return num;
//        }
//        //123.45
//        //(int)(123.45 * (maxDec * 10)) = 1234 / (maxDec * 10)
//        double factor = Math.pow(10, maxDec);
//        return (double)((int) (num * factor)) / factor;
//    }
//    
//    public static String formatBytes(long d){
//        double data = (double)d;
//        String type = "B";
//        if(data / 1000.0 >= 1.0){
//            type = "KB";
//            data /= 1000.0;
//            if(data / 1000.0 >= 1.0){
//                type = "MB";
//                data /= 1000.0;
//                if(data / 1000.0 >= 1.0){
//                    type = "GB";
//                    data /= 1000.0;
//                    if(data / 1000.0 >= 1.0){
//                        type = "TB";
//                        data /= 1000.0;
//                    }
//                }
//            }
//        }
//        data = removeDecimal(data, 1);
//        return data + " " + type;
//    }
//    
//    public static double map(double num, double numMin, double numMax, double mappedNumMin, double mappedNumMax){
//        double min1 = numMin;
//        double max1 = numMax;
//        double min2 = mappedNumMin;
//        double max2 = mappedNumMax;
//        return ((num - min1) * (max2 - min2) / (max1 - min1)) + min2;
//    }
//    
//    public static <T> T[] toArr(T... objs){
//        return objs;
//    }
//    
//    public static Object[] toObjArr(Object... objs){
//        return objs;
//    }
//    
//    public static boolean rename(File fileToRename, String renameTo){
//        if(!fileToRename.renameTo(new File(fileToRename.getParentFile(), renameTo))){
//            File batch = new File("renameBatch.bat");
//            try (PrintWriter writer = new PrintWriter(batch)){
//                writer.println("rename " + fileToRename.toString() + " " + renameTo);
//                writer.println("(goto) 2>nul & del \"%~f0\" & exit /b");
//                Util.runBatch(batch);
//                return true;
//            } catch (Exception e){
//                e.printStackTrace();
//                return false;
//            }
//        }
//        return true;
//    }
//    
//    public static boolean delete(File fileToDelete){
//        if(!fileToDelete.isFile()){
//            return true;
//        }
//        if(!fileToDelete.delete()){
//            File batch = new File("deleteBatch.bat");
//            try (PrintWriter writer = new PrintWriter(batch)){
//                writer.println("del /F" + fileToDelete.toString());
//                writer.println("(goto) 2>nul & del \"%~f0\" & exit /b");
//                Util.runBatch(batch);
//                return true;
//            } catch (Exception e){
//                e.printStackTrace();
//                return false;
//            }
//        }
//        return true;
//    }
//    
//    public static String getPCUserName(){
//        return System.getProperty("user.name");
//    }
//    
//    public static String toFirstUpperCase(String str){
//        return str.substring(0, 1).toUpperCase() + str.substring(1);
//    }
//    
//    public static <T extends Comparable> Object get(T[] list, Object key){
//        return get(list, toArr(key));
//    }
//    
//    public static <T extends Comparable> Object get(T[] list, Object[] keys){
//        for(int i = 0; i < list.length; i++){
//            T t = list[i];
//            for(int j = 0; j < keys.length; j++){
//                Object key = keys[j];
//                if(t.compareTo(key) == 0){
//                    return t;
//                }
//            }
//        }
//        return null;
//    }
//    
//    public static <T extends Comparable> Object binaryGet(ArrayList<? extends Comparable<? super T>> list, T key){
//        return binaryGet(list, Util.toArr(key));
//    }
//    
//    public static <T extends Comparable> Object binaryGet(ArrayList<? extends Comparable<? super T>> list, T[] keys){
//        for(T key : keys){
//            int index = binaryIndexOf(list, key);
//            if(index >= 0){
//                return list.get(index);
//            }
//        }
//        return null;
//    }
//    
//    public static <T> T binaryGet(T[] list, Object key){
//        return binaryGet(list, Util.toArr(key));
//    }
//    
//    public static <T> T binaryGet(T[] list, Object[] keys){
//        for(Object key : keys){
//            int index = binaryIndexOf(list, key);
//            if(index >= 0){
//                return list[index];
//            }
//        }
//        return null;
//    }
//    /**
//     * This function will take the obj variable, all the members of the arr and objs array and
//     * put them into a single array.
//     * @param <T>
//     * @param arr
//     * @param obj
//     * @param objs
//     * @return 
//     */
//    public static <T> T[] joinToArray(T[] arr, T obj, T... objs){
//        ArrayList<T> objList = new ArrayList<>();
//        objList.addAll(toList(arr));
//        objList.add(obj);
//        if(objs.length > 0){
//            objList.addAll(toList(objs));
//        }
//        return objList.toArray(arr);
//    }
//    
//    /**
//     * This function merges all 1-D elements of the arrays passed into a single new array.If given [1, 5] and [3, 2], this would return [1, 5, 3, 2].
//     * If given [1, 5], [3, 2], and [10, 7], this would return [1, 5, 3, 2, 10, 7].
// If given [1, 5,], [3, 2], and [[10, 7], [4, 8]], this would return [1, 5, 3, 2, [10, 7], [4, 8]].
//     * @param <T>
//     * @param arr1
//     * @param arr2
//     * @param arrs
//     * @return 
//     */
//    public static <T> T[] mergeArrays(T[] arr1, T[] arr2, T[]... arrs){
//        ArrayList<Object> objList = new ArrayList<>();
//        objList.addAll(toList(arr1));
//        objList.addAll(toList(arr2));
//        if(arrs.length > 0){
//            objList.addAll(toList(arrs));
//        }
//        return objList.toArray(arr1);
//    }
//}