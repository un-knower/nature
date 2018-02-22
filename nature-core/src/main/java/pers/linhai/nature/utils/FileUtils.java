package pers.linhai.nature.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;


public abstract class FileUtils
{
    /**
     * 默认缓存大小
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024 * 5;
    
    /**
     * 获取文件夹下的文件名列表，以数组返回
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-28
     * @param dir
     * @return
     */
    public static String[] getDirectoryFileNames(String dir)
    {
        createDir(dir);
        File dirFile = new File(dir);
        return dirFile.list();
    }
    
    /**
     * 获取文件夹下的文件名列表，以数组返回
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-28
     * @param dir
     * @return
     */
    public static String[] getDirectoryFileNames(String dir, final String suffix)
    {
        createDir(dir);
        File dirFile = new File(dir);
        return dirFile.list(new FilenameFilter(){
            public boolean accept(File dir, String name)
            {
                if(name.endsWith(suffix))
                {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 删除文件
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-28
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        File file = new File(filePath);
        if (file.isFile() && file.exists())
        {
            return file.delete();
        }
        return false;
    }

    /**
     * 获取文件输入流对象
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-28
     * @param filePath
     * @return
     */
    public static InputStream getFileInputStream(String filePath)
    {
        File file = new File(filePath);
        try
        {
            if (file.isFile() && file.exists())
            {
                return new FileInputStream(file);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将文本内容写入指定文件
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-25
     * @param content
     * @param filePath
     */
    public static void saveFile(String content, String filePath, boolean isAppend)
    {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        StringReader stringReader = null;
        try
        {
            // 字符串读取器
            stringReader = new StringReader(content);

            // 缓冲读取流
            bufferedReader = new BufferedReader(stringReader);

            // 缓冲写入流
            bufferedWriter = new BufferedWriter(new FileWriter(filePath, isAppend));

            // 字符缓冲区
            char buf[] = new char[DEFAULT_BUFFER_SIZE];

            int len = DEFAULT_BUFFER_SIZE;

            while ((len = bufferedReader.read(buf, 0, DEFAULT_BUFFER_SIZE)) != -1)
            {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(stringReader);
            close(bufferedReader);
            close(bufferedWriter);
        }
    }

    /**
     * 将文件内容写入指定文件
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-25
     * @param file
     * @param filePath
     */
    public static void saveFile(File file, String filePath, String fileName)
    {
        InputStream in = null;
        try
        {
            in = new FileInputStream(file);
            saveFile(in, filePath, fileName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(in);
        }
    }

    /**
     * 文件保存：输入流对象，文件路径
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-25
     * @param in
     * @param filePath
     */
    public static void saveFile(InputStream in, String filePath, String fileName) throws Exception
    {
        OutputStream out = null;
        try
        {
            createDir(filePath);
            out = new FileOutputStream(filePath + File.separator + fileName);

            // 字符缓冲区
            byte buf[] = new byte[DEFAULT_BUFFER_SIZE];

            int len = DEFAULT_BUFFER_SIZE;

            while ((len = in.read(buf, 0, DEFAULT_BUFFER_SIZE)) != -1)
            {
                out.write(buf, 0, len);
            }
            out.flush();
        }
        catch (Throwable e)
        {
            throw new Exception("文件保存异常：", e);
        }
        finally
        {
            close(in);
            close(out);
        }
    }

    /**
     * 创建目录
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-2-1
     * @param filePath
     */
    public static void createDir(String filePath)
    {
        File dir = new File(filePath);
        createDir(dir);
    }
    
    /**
     * 创建目录
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-2-1
     * @param filePath
     */
    public static void createDir(File dir)
    {
        if (!dir.exists() && !dir.isDirectory())
        {
            dir.mkdirs();
        }
    }

    /**
     * 将文件读取到String
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-25
     * @param file
     * @return
     */
    public static String fileToString(File file)
    {
        try
        {
            return inputStreamToString(new FileInputStream(file));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取输入流中的指定长度字符
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-31
     * @param in
     * @param size
     * @return
     */
    public static String getInputStreamSubContent(InputStream in, int size)
    {
        BufferedReader bufferedReader = null;
        InputStreamReader reader = null;
        StringWriter writer = null;
        String content = null;
        try
        {
            reader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(reader);
            writer = new StringWriter();

            // 读取指定长度到缓冲区
            int tempCode = 0;

            // 读取总长度
            int len = 0;
            while ((tempCode = bufferedReader.read()) != -1)
            {
                if (len >= size)
                {
                    break;
                }
                else
                {
                    writer.write((char) tempCode);
                }
                len++;
            }
            writer.flush();
            content = writer.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(in);
            close(bufferedReader);
            close(reader);
            close(writer);
        }
        return content;
    }

    /**
     * 将文件读取到String
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-25
     * @param file
     * @return
     */
    public static String inputStreamToString(InputStream in)
    {
        BufferedReader bufferedReader = null;
        InputStreamReader reader = null;
        StringWriter writer = null;
        String content = null;
        try
        {
            reader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(reader);
            writer = new StringWriter();
            // 字符缓冲区
            char[] buf = new char[DEFAULT_BUFFER_SIZE];

            int len = 0;

            while ((len = bufferedReader.read(buf, 0, DEFAULT_BUFFER_SIZE)) != -1)
            {
                writer.write(buf, 0, len);
            }
            writer.flush();
            content = writer.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(in);
            close(bufferedReader);
            close(reader);
            close(writer);
        }
        return content;
    }

    /**
     * 将文件读取到String
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-25
     * @param filePath
     * @return
     */
    public static String fileToString(String filePath)
    {
        File file = new File(filePath);
        if (file.isFile() && file.exists())
        {
            return fileToString(file);
        }
        return null;
    }

    /**
     * 将对象序列化到指定文件
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-2-1
     * @param obj
     * @param filePath
     */
    public static void objectToFile(Object obj, String filePath, String fileName) throws Exception
    {
        ObjectOutputStream objectOutputStream = null;
        try
        {
            createDir(filePath);
            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath + File.separator + fileName)));
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
        }
        catch (FileNotFoundException e)
        {
            throw new Exception(filePath + "文件没有找到:", e);
        }
        catch (IOException e)
        {
            throw new Exception(filePath + "序列化IO异常:", e);
        }
        finally
        {
            close(objectOutputStream);
        }
    }

    /**
     * 文件反序列化到对象
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-2-1
     * @param filePath
     * @return
     * @throws FileException
     */
    public static Object fileToObject(String filePath) throws Exception
    {
        return fileToObject(new File(filePath));
    }

    /**
     * 文件反序列化到对象
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-2-1
     * @param filePath
     * @return
     * @throws FileException
     */
    public static Object fileToObject(File file) throws Exception
    {
        ObjectInputStream objectInputStream = null;
        Object object = null;
        try
        {
            objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));

            object = objectInputStream.readObject();

            return object;
        }
        catch (FileNotFoundException e)
        {
            throw new Exception(file.getPath() + "这个文件没有找到:", e);
        }
        catch (ClassNotFoundException e)
        {
            throw new Exception(file.getPath() + "反序列化异常:", e);
        }
        catch (IOException e)
        {
            throw new Exception(file.getPath() + "出现反序列化IO异常:", e);
        }
        finally
        {
            close(objectInputStream);
        }
    }

    /**
     * 关闭流通用方法
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-1-25
     * @param os
     */
    public static void close(Closeable os)
    {
        if (os != null)
        {
            try
            {
                os.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件的大小
     * @author 姓名:吕枫桔 工号:lvfengju WX159608 TODO
     * @since FTDS V100R001, 2013-3-7
     * @param fileName
     * @return
     */
    public long getFileSize(String fileName)
    {
        return new File(fileName).length();
    }

    /**
     * 校验一个路径是否有效
     * @author 姓名：李林海 工号 lilinhai WX160353
     * @since FTDS V100R001, 2013-5-16
     * @param dirPath
     * @return
     */
    public boolean isDirEffective(String dirPath)
    {
        try
        {
            createDir(dirPath);
            File dir = new File(dirPath);
            if (dir.exists())
            {
                dir.delete();
                return true;
            }
            return false;
        }
        catch (Throwable e)
        {
            return false;
        }
    }
}
