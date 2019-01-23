package base.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * File operation related utilities
 */
public class JFileUtils {
    /**
     * Recusive specific dir and get all files and paths
     * @param file The root dir name
     * @param resultFileName  The returned dir and file names
     * @return   All of the files and paths in the dir
     */
    public static List<String> recusiveDir(File file, List<String> resultFileName){
        File[] files = file.listFiles();
        if(files==null)
            return resultFileName;// 判断目录下是不是空的
        for (File f : files) {
            if(f.isDirectory()){// 判断是否文件夹
                resultFileName.add(f.getPath());
                recusiveDir(f,resultFileName);// 调用自身,查找子目录
            }else
                resultFileName.add(f.getPath());
        }
        return resultFileName;
    }


    /**
     * Recusive specific dir and get all files with a specific extension
     * @param file The root dir name
     * @param resultFileName The returned dir and file names
     * @param extension The file extension you specified, Only those files with this extension will be returned
     * @return All of the files with the specific extension
     */
    public static List<String> recusiveDir(File file, List<String> resultFileName , String extension){
        List<String> allFiles = recusiveDir(file,resultFileName);
        List<String> resultFiles = new ArrayList<>();
        for(String fileName: allFiles) {
            if(fileName.endsWith(extension)) {
                resultFiles.add(fileName);
            }
        }
        return resultFiles;
    }

    /**
     * Read file from a file path
     * @param filePath  The file path
     * @return The file content as string
     * @throws Exception  This method will throw an exception when file is not found
     */
    public static String readFile(String filePath) throws Exception{
        return FileUtils.readFileToString(new File(filePath),"UTF-8");
    }


    /**
     * Write string content to a specific file path
     * @param filePath  The file path you want to write
     * @param content  The string content
     * @throws Exception  Io exception may be thrown from this method
     */
    public static void writeFile(String filePath,String content) throws Exception {
        FileUtils.write(new File(filePath),content,"UTF-8");
    }

    /**
     * Copy a source file to a destination file
     * @param srcFileName Src file path
     * @param destFileName  Destination file path
     * @throws Exception  Io exception may be thrown from this method
     */
    public static void copyFile(String srcFileName,String destFileName) throws Exception {
        File srcFile = new File(srcFileName);
        File dstFile = new File(destFileName);
        if(dstFile.isDirectory()) {
            FileUtils.copyFileToDirectory(srcFile,dstFile);
        }else {
            FileUtils.copyFile(srcFile,dstFile);
        }
    }

    /**
     * Get current project root dir
     * @return  The root dir of current project
     */
    public static String getProjRootDir(){
        return System.getProperty("user.dir");
    }
}
