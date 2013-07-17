/* Chronos - Game Development Toolkit for Java game developers. The
 * original source remains:
 * 
 * Copyright (c) 2013 Miguel Gonzalez http://my-reality.de
 * 
 * This source is provided under the terms of the BSD License.
 * 
 * Copyright (c) 2013, Chronos
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 *  * Redistributions of source code must retain the above 
 *    copyright notice, this list of conditions and the 
 *    following disclaimer.
 *  * Redistributions in binary form must reproduce the above 
 *    copyright notice, this list of conditions and the following 
 *    disclaimer in the documentation and/or other materials provided 
 *    with the distribution.
 *  * Neither the name of the Chronos/my Reality Development nor the names of 
 *    its contributors may be used to endorse or promote products 
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 */
package de.myreality.chronos.logging;

import java.util.logging.Level;

/**
 * Provides logging output
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.8alpha
 * @version 0.8alpha
 */
public class ChronosLogger {
	
	private static LogSystem system;
    
    static {
            system = new ChronosLogSystem();
    }
    
    /**
     * Logging an error
     * 
     * @param message Message to show
     */
    public static void error(String message) {
            system.error(message);
    }
    
    /**
     * Logging an error
     * 
     * @param message Message to show
     * @param e Throwable error
     */
    public static void error(String message, Throwable e) {
            system.error(message, e);
    }
    
    /**
     * Logging an error
     * 
     * @param e Throwable error
     */
    public static void error(Throwable e) {
            system.error(e);
    }
    
    /**
     * Logging a warning
     * 
     * @param message Message to show
     */
    public static void warn(String message) {
            system.warn(message);
    }
    
    /**
     * Logging a warning
     * 
     * @param message Message to show
     * @param e Throwable error
     */
    public static void warn(String message, Throwable e) {
            system.warn(message, e);
    }
    
    /**
     * Logging an information
     * 
     * @param message Message to show
     */
    public static void info(String message) {
            system.info(message);
    }
    
    /**
     * Logging a debug test
     * 
     * @param message Message to show
     */
    public static void debug(String message) {
            system.debug(message);
    }
    
    
    /**
     * Sets a new log system
     * 
     * @param logSystem new log system
     */
    public static void setLogSystem(LogSystem logSystem) {
            system = logSystem;
    }
    
    
    /**
     * Change the level of the logging
     * 
     * @param level logging level
     */
    public static void setLevel(Level level) {
            system.setLevel(level);
    }
}
