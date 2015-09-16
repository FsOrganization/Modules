/*
 * Copyright 2011 Király Attila
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fla.common.verification;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.fla.common.verification.cage.Cage;
import com.fla.common.verification.cage.GCage;

/**
 * A quick start example showcasing the usage of Cage. Generates a single
 * captcha image with the "G" template and writes it to a file.
 * 
 * @author akiraly
 */
public class QuickStart {
	public static void main(String[] args) throws IOException {
		final Cage cage = new GCage();
		final OutputStream os = new FileOutputStream("captcha.png", false);
		String finalCode = null;
		try 
		{
			String code = cage.getTokenGenerator().next();
			code = code.substring(0, 5);
			cage.draw(code, os);
			System.out.print(code);
		} finally {
			os.close();
		}
	}
}
