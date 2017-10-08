package pers.linhai.nature.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class IOUtils
{
    
    /**
     * 默认缓存大小
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024 * 5;
    
    public static byte[] inputStreamToBytes(InputStream in) throws IOException
    {
        ByteArrayOutputStream out = null;
        try
        {
            out = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
            
            // 字符缓冲区
            byte buf[] = new byte[DEFAULT_BUFFER_SIZE];
            
            int len = DEFAULT_BUFFER_SIZE;
            
            while ((len = in.read(buf, 0, DEFAULT_BUFFER_SIZE)) != -1)
            {
                out.write(buf, 0, len);
            }
            out.flush();
            return out.toByteArray();
        }
        catch (Throwable e)
        {
            throw new IOException("文件保存异常：", e);
        }
        finally
        {
            close(in);
            close(out);
        }
    }
    
    public static String inputStreamToString(InputStream in, String enCoding) throws IOException
    {
        try
        {
            return new String(inputStreamToBytes(in), enCoding);
        }
        catch (IOException e)
        {
            throw e;
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String inputStreamToString(InputStream in) throws IOException
    {
        return inputStreamToString(in, "UTF-8");
    }
    
    /**
     * UTF-8 charset string.
     * <p>Where possible, use {@link StandardCharsets#UTF_8} instead,
     * as using the String constant may slow things down.
     * @see StandardCharsets#UTF_8
     */
    public static final String UTF_8 = StandardCharsets.UTF_8.name();
    
    private IOUtils()
    {
    } // no instance
    
    /**
     * Closes all given <tt>Closeable</tt>s.  Some of the
     * <tt>Closeable</tt>s may be null; they are
     * ignored.  After everything is closed, the method either
     * throws the first exception it hit while closing, or
     * completes normally if there were no exceptions.
     * 
     * @param objects
     *          objects to call <tt>close()</tt> on
     */
    public static void close(Closeable... objects) throws IOException
    {
        close(Arrays.asList(objects));
    }
    
    /**
     * Closes all given <tt>Closeable</tt>s.
     * @see #close(Closeable...)
     */
    public static void close(Iterable< ? extends Closeable> objects) throws IOException
    {
        Throwable th = null;
        
        for (Closeable object : objects)
        {
            try
            {
                if (object != null)
                {
                    object.close();
                }
            }
            catch (Throwable t)
            {
                addSuppressed(th, t);
                if (th == null)
                {
                    th = t;
                }
            }
        }
        
        if (th != null)
        {
            throw rethrowAlways(th);
        }
    }
    
    /**
     * Closes all given <tt>Closeable</tt>s, suppressing all thrown exceptions.
     * Some of the <tt>Closeable</tt>s may be null, they are ignored.
     * 
     * @param objects
     *          objects to call <tt>close()</tt> on
     */
    public static void closeWhileHandlingException(Closeable... objects)
    {
        closeWhileHandlingException(Arrays.asList(objects));
    }
    
    /**
     * Closes all given <tt>Closeable</tt>s, suppressing all thrown exceptions.
     * @see #closeWhileHandlingException(Closeable...)
     */
    public static void closeWhileHandlingException(Iterable< ? extends Closeable> objects)
    {
        for (Closeable object : objects)
        {
            try
            {
                if (object != null)
                {
                    object.close();
                }
            }
            catch (Throwable t)
            {
            }
        }
    }
    
    /** adds a Throwable to the list of suppressed Exceptions of the first Throwable
     * @param exception this exception should get the suppressed one added
     * @param suppressed the suppressed exception
     */
    private static void addSuppressed(Throwable exception, Throwable suppressed)
    {
        if (exception != null && suppressed != null)
        {
            exception.addSuppressed(suppressed);
        }
    }
    
    /**
     * Wrapping the given {@link InputStream} in a reader using a {@link CharsetDecoder}.
     * Unlike Java's defaults this reader will throw an exception if your it detects 
     * the read charset doesn't match the expected {@link Charset}. 
     * <p>
     * Decoding readers are useful to load configuration files, stopword lists or synonym files
     * to detect character set problems. However, it's not recommended to use as a common purpose 
     * reader.
     * 
     * @param stream the stream to wrap in a reader
     * @param charSet the expected charset
     * @return a wrapping reader
     */
    public static Reader getDecodingReader(InputStream stream, Charset charSet)
    {
        final CharsetDecoder charSetDecoder = charSet.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        return new BufferedReader(new InputStreamReader(stream, charSetDecoder));
    }
    
    /**
     * Opens a Reader for the given resource using a {@link CharsetDecoder}.
     * Unlike Java's defaults this reader will throw an exception if your it detects 
     * the read charset doesn't match the expected {@link Charset}. 
     * <p>
     * Decoding readers are useful to load configuration files, stopword lists or synonym files
     * to detect character set problems. However, it's not recommended to use as a common purpose 
     * reader.
     * @param clazz the class used to locate the resource
     * @param resource the resource name to load
     * @param charSet the expected charset
     * @return a reader to read the given file
     * 
     */
    public static Reader getDecodingReader(Class< ? > clazz, String resource, Charset charSet) throws IOException
    {
        InputStream stream = null;
        boolean success = false;
        try
        {
            stream = clazz.getResourceAsStream(resource);
            final Reader reader = getDecodingReader(stream, charSet);
            success = true;
            return reader;
        }
        finally
        {
            if (!success)
            {
                IOUtils.close(stream);
            }
        }
    }
    
    /**
     * Deletes all given files, suppressing all thrown IOExceptions.
     * <p>
     * Some of the files may be null, if so they are ignored.
     */
    public static void deleteFilesIgnoringExceptions(Path... files)
    {
        deleteFilesIgnoringExceptions(Arrays.asList(files));
    }
    
    /**
     * Deletes all given files, suppressing all thrown IOExceptions.
     * <p>
     * Some of the files may be null, if so they are ignored.
     */
    public static void deleteFilesIgnoringExceptions(Collection< ? extends Path> files)
    {
        for (Path name : files)
        {
            if (name != null)
            {
                try
                {
                    Files.delete(name);
                }
                catch (Throwable ignored)
                {
                    // ignore
                }
            }
        }
    }
    
    /**
     * Deletes all given <tt>Path</tt>s, if they exist.  Some of the
     * <tt>File</tt>s may be null; they are
     * ignored.  After everything is deleted, the method either
     * throws the first exception it hit while deleting, or
     * completes normally if there were no exceptions.
     * 
     * @param files files to delete
     */
    public static void deleteFilesIfExist(Path... files) throws IOException
    {
        deleteFilesIfExist(Arrays.asList(files));
    }
    
    /**
     * Deletes all given <tt>Path</tt>s, if they exist.  Some of the
     * <tt>File</tt>s may be null; they are
     * ignored.  After everything is deleted, the method either
     * throws the first exception it hit while deleting, or
     * completes normally if there were no exceptions.
     * 
     * @param files files to delete
     */
    public static void deleteFilesIfExist(Collection< ? extends Path> files) throws IOException
    {
        Throwable th = null;
        
        for (Path file : files)
        {
            try
            {
                if (file != null)
                {
                    Files.deleteIfExists(file);
                }
            }
            catch (Throwable t)
            {
                addSuppressed(th, t);
                if (th == null)
                {
                    th = t;
                }
            }
        }
        
        if (th != null)
        {
            throw rethrowAlways(th);
        }
    }
    
    /**
     * Deletes one or more files or directories (and everything underneath it).
     * 
     * @throws IOException if any of the given files (or their subhierarchy files in case
     * of directories) cannot be removed.
     */
    public static void rm(Path... locations) throws IOException
    {
        LinkedHashMap<Path, Throwable> unremoved = rm(new LinkedHashMap<Path, Throwable>(), locations);
        if (!unremoved.isEmpty())
        {
            StringBuilder b = new StringBuilder("Could not remove the following files (in the order of attempts):\n");
            for (Map.Entry<Path, Throwable> kv : unremoved.entrySet())
            {
                b.append("   ").append(kv.getKey().toAbsolutePath()).append(": ").append(kv.getValue()).append("\n");
            }
            throw new IOException(b.toString());
        }
    }
    
    private static LinkedHashMap<Path, Throwable> rm(final LinkedHashMap<Path, Throwable> unremoved, Path... locations)
    {
        if (locations != null)
        {
            for (Path location : locations)
            {
                // TODO: remove this leniency!
                if (location != null && Files.exists(location))
                {
                    try
                    {
                        Files.walkFileTree(location, new FileVisitor<Path>()
                        {
                            @Override
                            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
                            {
                                return FileVisitResult.CONTINUE;
                            }
                            
                            @Override
                            public FileVisitResult postVisitDirectory(Path dir, IOException impossible) throws IOException
                            {
                                assert impossible == null;
                                
                                try
                                {
                                    Files.delete(dir);
                                }
                                catch (IOException e)
                                {
                                    unremoved.put(dir, e);
                                }
                                return FileVisitResult.CONTINUE;
                            }
                            
                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
                            {
                                try
                                {
                                    Files.delete(file);
                                }
                                catch (IOException exc)
                                {
                                    unremoved.put(file, exc);
                                }
                                return FileVisitResult.CONTINUE;
                            }
                            
                            @Override
                            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
                            {
                                if (exc != null)
                                {
                                    unremoved.put(file, exc);
                                }
                                return FileVisitResult.CONTINUE;
                            }
                        });
                    }
                    catch (IOException impossible)
                    {
                        throw new AssertionError("visitor threw exception", impossible);
                    }
                }
            }
        }
        return unremoved;
    }
    
    /**
     * This utility method takes a previously caught (non-null)
     * {@code Throwable} and rethrows either the original argument
     * if it was a subclass of the {@code IOException} or an 
     * {@code RuntimeException} with the cause set to the argument.
     * 
     * <p>This method <strong>never returns any value</strong>, even though it declares
     * a return value of type {@link Error}. The return value declaration
     * is very useful to let the compiler know that the code path following
     * the invocation of this method is unreachable. So in most cases the
     * invocation of this method will be guarded by an {@code if} and
     * used together with a {@code throw} statement, as in:
     * </p>
     * <pre>{@code
     *   if (t != null) throw IOUtils.rethrowAlways(t)
     * }
     * </pre>
     * 
     * @param th The throwable to rethrow, <strong>must not be null</strong>.
     * @return This method always results in an exception, it never returns any value. 
     *         See method documentation for detailsa and usage example.
     * @throws IOException if the argument was an instance of IOException
     * @throws RuntimeException with the {@link RuntimeException#getCause()} set
     *         to the argument, if it was not an instance of IOException. 
     */
    public static Error rethrowAlways(Throwable th) throws IOException, RuntimeException
    {
        if (th == null)
        {
            throw new AssertionError("rethrow argument must not be null.");
        }
        
        if (th instanceof IOException)
        {
            throw (IOException) th;
        }
        
        if (th instanceof RuntimeException)
        {
            throw (RuntimeException) th;
        }
        
        if (th instanceof Error)
        {
            throw (Error) th;
        }
        
        throw new RuntimeException(th);
    }
    
    // following methods are package-private for testing ONLY
    
    // note: requires a real or fake linux filesystem!
    static boolean spinsLinux(Path path) throws IOException
    {
        FileStore store = getFileStore(path);
        
        // if fs type is tmpfs, it doesn't spin.
        // this won't have a corresponding block device
        if ("tmpfs".equals(store.type()))
        {
            return false;
        }
        
        // get block device name
        String devName = store.name();
        
        // not a device (e.g. NFS server)
        if (!devName.startsWith("/"))
        {
            return true;
        }
        
        // resolve any symlinks to real block device (e.g. LVM)
        // /dev/sda0 -> sda0
        // /devices/XXX -> sda0
        devName = path.getRoot().resolve(devName).toRealPath().getFileName().toString();
        
        // now try to find the longest matching device folder in /sys/block
        // (that starts with our dev name):
        Path sysinfo = path.getRoot().resolve("sys").resolve("block");
        Path devsysinfo = null;
        int matchlen = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sysinfo))
        {
            for (Path device : stream)
            {
                String name = device.getFileName().toString();
                if (name.length() > matchlen && devName.startsWith(name))
                {
                    devsysinfo = device;
                    matchlen = name.length();
                }
            }
        }
        
        if (devsysinfo == null)
        {
            return true; // give up
        }
        
        // read first byte from rotational, it's a 1 if it spins.
        Path rotational = devsysinfo.resolve("queue").resolve("rotational");
        try (InputStream stream = Files.newInputStream(rotational))
        {
            return stream.read() == '1';
        }
    }
    
    // Files.getFileStore(Path) useless here!
    // don't complain, just try it yourself
    static FileStore getFileStore(Path path) throws IOException
    {
        FileStore store = Files.getFileStore(path);
        String mount = getMountPoint(store);
        
        // find the "matching" FileStore from system list, it's the one we want, but only return
        // that if it's unambiguous (only one matching):
        FileStore sameMountPoint = null;
        for (FileStore fs : path.getFileSystem().getFileStores())
        {
            if (mount.equals(getMountPoint(fs)))
            {
                if (sameMountPoint == null)
                {
                    sameMountPoint = fs;
                }
                else
                {
                    // more than one filesystem has the same mount point; something is wrong!
                    // fall back to crappy one we got from Files.getFileStore
                    return store;
                }
            }
        }
        
        if (sameMountPoint != null)
        {
            // ok, we found only one, use it:
            return sameMountPoint;
        }
        else
        {
            // fall back to crappy one we got from Files.getFileStore
            return store;
        }
    }
    
    // these are hacks that are not guaranteed, may change across JVM versions, etc.
    static String getMountPoint(FileStore store)
    {
        String desc = store.toString();
        int index = desc.lastIndexOf(" (");
        if (index != -1)
        {
            return desc.substring(0, index);
        }
        else
        {
            return desc;
        }
    }
    
}
