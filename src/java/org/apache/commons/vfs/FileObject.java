/* ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.commons.vfs;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represents a file, and is used to access the content and
 * structure of the file.
 *
 * <p>Files are arranged in a hierarchy.  Each hierachy forms a
 * <i>file system</i>.  A file system represents things like a local OS
 * file system, a windows share, an HTTP server, or the contents of a Zip file.
 *
 * <p>There are two types of files: <i>Folders</i>, which contain other files,
 * and <i>normal files</i>, which contain data, or <i>content</i>.  A folder may
 * not have any content, and a normal file cannot contain other files.
 *
 * <h4>File Naming</h4>
 *
 * <p>TODO - write this.
 *
 * <h4>Reading and Writing a File</h4>
 *
 * <p>Reading and writing a file, and all other operations on the file's
 * <i>content</i>, is done using the {@link FileContent} object returned
 * by {@link #getContent}.
 *
 * <h4>Creating and Deleting a File</h4>
 *
 * <p>A file is created using either {@link #createFolder}, {@link #createFile},
 * or by writing to the file using one of the {@link FileContent} methods.
 *
 * <p>A file is deleted using {@link #delete}.  Recursive deletion can be
 * done using {@link #delete(FileSelector)}.
 *
 * <h4>Finding Files</h4>
 *
 * <p>Other files in the <i>same</i> file system as this file can be found
 * using:
 * <ul>
 * <li>{@link #resolveFile} to find another file relative to this file.
 * <li>{@link #getChildren} and {@link #getChild} to find the children of this file.
 * <li>{@link #getParent} to find the folder containing this file.
 * <li>{@link #getFileSystem} to find another file in the same file system.
 * </ul>
 *
 * <p>To find files in another file system, use a {@link FileSystemManager}.
 *
 * @see FileSystemManager
 * @see FileContent
 * @see FileName
 *
 * @author <a href="mailto:adammurdoch@apache.org">Adam Murdoch</a>
 * @version $Revision: 1.6 $ $Date: 2002/07/05 03:21:54 $
 */
public interface FileObject
{
    /**
     * Returns the name of this file.
     */
    FileName getName();

    /**
     * Returns a URL representing this file.
     */
    URL getURL() throws FileSystemException;

    /**
     * Determines if this file exists.
     *
     * @return
     *      <code>true</code> if this file exists, <code>false</code> if not.
     *
     * @throws FileSystemException
     *      On error determining if this file exists.
     */
    boolean exists() throws FileSystemException;

    /**
     * Determines if this file can be read (only files that exist can be read).
     *
     * @return
     *      <code>true</code> if this file is readable, <code>false</code> if not.
     *
     * @throws FileSystemException
     *      On error determining if this file exists.
     */
    boolean isReadable() throws FileSystemException;

    /**
     * Determines if this file can be written to.
     *
     * @return
     *      <code>true</code> if this file is writeable, <code>false</code> if not.
     *
     * @throws FileSystemException
     *      On error determining if this file exists.
     */
    boolean isWriteable() throws FileSystemException;

    /**
     * Returns this file's type.
     *
     * @return
     *      Either {@link FileType#FILE} or {@link FileType#FOLDER}.  Never
     *      returns null.
     *
     * @throws FileSystemException
     *      If the file does not exist, or on error determining the file's type.
     */
    FileType getType() throws FileSystemException;

    /**
     * Returns the folder that contains this file.
     *
     * @return
     *      The folder that contains this file.  Returns null if this file is
     *      the root of a file system.
     *
     * @throws FileSystemException
     *      On error finding the file's parent.
     */
    FileObject getParent() throws FileSystemException;

    /**
     * Returns the file system that contains this file.
     *
     * @return The file system.
     */
    FileSystem getFileSystem() throws FileSystemException;

    /**
     * Lists the children of this file.
     *
     * @return
     *      An array containing the children of this file.  The array is
     *      unordered.  If the file does not have any children, a zero-length
     *      array is returned.  This method never returns null.
     *
     * @throws FileSystemException
     *      If this file does not exist, or is not a folder, or on error
     *      listing this file's children.
     */
    FileObject[] getChildren() throws FileSystemException;

    /**
     * Returns a child of this file.  Note that this method returns <code>null</code>
     * when the child does not exist.  This differs from
     * {@link #resolveFile( String, NameScope)} which never returns null.
     *
     * @param name
     *      The name of the child.
     *
     * @return
     *      The child, or null if there is no such child.
     *
     * @throws FileSystemException
     *      If this file does not exist, or is not a folder, or on error
     *      determining this file's children.
     */
    FileObject getChild( String name ) throws FileSystemException;

    /**
     * Finds a file, relative to this file.  Refer to {@link NameScope}
     * for a description of how names are resolved in the different scopes.
     *
     * @param name
     *      The name to resolve.
     *
     * @return
     *      The file.
     *
     * @throws FileSystemException
     *      On error parsing the path, or on error finding the file.
     */
    FileObject resolveFile( String name, NameScope scope )
        throws FileSystemException;

    /**
     * Finds a file, relative to this file.  Equivalent to calling
     * <code>resolveFile( path, NameScope.FILE_SYSTEM )</code>.
     *
     * @param path
     *      The path of the file to locate.  Can either be a relative
     *      path or an absolute path.
     *
     * @return
     *      The file.
     *
     * @throws FileSystemException
     *      On error parsing the path, or on error finding the file.
     */
    FileObject resolveFile( String path ) throws FileSystemException;

    /**
     * Finds the set of matching descendents of this file, in depthwise order.
     *
     * @param selector The selector to use to select matching files.
     *
     * @return A list of matching {@link FileObject} files.  The files are
     *      returned in depthwise order (that is, a child appears in the
     *      list before its parent).
     *
     * @todo Use the same return type for getChildren() and findFiles().
     */
    FileObject[] findFiles( FileSelector selector ) throws FileSystemException;

    /**
     * Deletes this file.  Does nothing if this file does not exist.  Does
     * not delete any descendents of this file, use {@link #delete(FileSelector)}
     * for that.
     *
     * @throws FileSystemException
     *      If this file is a non-empty folder, or if this file is read-only,
     *      or on error deleteing this file.
     */
    void delete() throws FileSystemException;

    /**
     * Deletes all descendents of this file that match a selector.  Does
     * nothing if this file does not exist.
     *
     * <p>This method is not transactional.  If it fails and throws an
     * exception, this file will potentially only be partially deleted.
     *
     * @param selector The selector to use to select which files to delete.
     *
     * @throws FileSystemException
     *      If this file or one of its descendents is read-only, or on error
     *      deleting this file or one of its descendents.
     */
    void delete( FileSelector selector ) throws FileSystemException;

    /**
     * Creates this folder, if it does not exist.  Also creates any ancestor
     * folders which do not exist.  This method does nothing if the folder
     * already exists.
     *
     * @throws FileSystemException
     *      If the folder already exists with the wrong type, or the parent
     *      folder is read-only, or on error creating this folder or one of
     *      its ancestors.
     */
    void createFolder() throws FileSystemException;

    /**
     * Creates this file, if it does not exist.  Also creates any ancestor
     * folders which do not exist.  This method does nothing if the file
     * already exists and is a file.
     *
     * @throws FileSystemException
     *      If the file already exists with the wrong type, or the parent
     *      folder is read-only, or on error creating this file or one of
     *      its ancestors.
     */
    void createFile() throws FileSystemException;

    /**
     * Copies another file, and all its descendents, to this file.
     *
     * If this file does not exist, it is created.  Its parent folder is also
     * created, if necessary.  If this file does exist, it is deleted first.
     *
     * <p>This method is not transactional.  If it fails and throws an
     * exception, this file will potentially only be partially copied.
     *
     * @param srcFile The source file to copy.
     * @param selector The selector to use to select which files to copy.
     *
     * @throws FileSystemException
     *      If this file is read-only, or if the source file does not exist,
     *      or on error copying the file.
     */
    void copyFrom( FileObject srcFile, FileSelector selector )
        throws FileSystemException;

    /**
     * Returns this file's content.  The {@link FileContent} returned by this
     * method can be used to read and write the content of the file.
     *
     * <p>This method can be called if the file does not exist, and
     * the returned {@link FileContent} can be used to create the file
     * by writing its content.
     *
     * @return
     *      This file's content.
     *
     * @throws FileSystemException
     *      If this file is a folder.
     */
    FileContent getContent() throws FileSystemException;

    /**
     * Closes this file, and its content.  This method is a hint to the
     * implementation that it can release any resources asociated with
     * the file.
     *
     * <p>The file object can continue to be used after this method is called.
     *
     * @see FileContent#close
     *
     * @throws FileSystemException
     *      On error closing the file.
     */
    void close() throws FileSystemException;
}
