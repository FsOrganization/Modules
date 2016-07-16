package org.sword.lang;

import java.io.ByteArrayInputStream;
  import java.io.IOException;
  
  /*
  2    * Copyright 2005-2006 Sun Microsystems, Inc.  All Rights Reserved.
  3    * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
  4    *
  5    * This code is free software; you can redistribute it and/or modify it
  6    * under the terms of the GNU General Public License version 2 only, as
  7    * published by the Free Software Foundation.  Sun designates this
  8    * particular file as subject to the "Classpath" exception as provided
  9    * by Sun in the LICENSE file that accompanied this code.
 10    *
 11    * This code is distributed in the hope that it will be useful, but WITHOUT
 12    * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 13    * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 14    * version 2 for more details (a copy is included in the LICENSE file that
 15    * accompanied this code).
 16    *
 17    * You should have received a copy of the GNU General Public License version
 18    * 2 along with this work; if not, write to the Free Software Foundation,
 19    * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 20    *
 21    * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 22    * CA 95054 USA or visit www.sun.com if you need additional information or
 23    * have any questions.
 24    */
   public class ByteInputStream extends ByteArrayInputStream {
       private static final byte[] EMPTY_ARRAY = new byte[0];
   
       public ByteInputStream() {
           this(EMPTY_ARRAY, 0);
       }
   
       public ByteInputStream(byte buf[], int length) {
           super(buf, 0, length);
       }
   
       public ByteInputStream(byte buf[], int offset, int length) {
           super(buf, offset, length);
       }
   
       public byte[] getBytes() {
           return buf;
       }
   
       public int getCount() {
           return count;
       }
   
       public void close() throws IOException {
           reset();
       }
   
       public void setBuf(byte[] buf) {
           this.buf = buf;
           this.pos = 0;
           this.count = buf.length;
       }
   }
