/*
 * Copyright (C) 2016 David Dejori, Alex Lardschneider
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.sasabz.android.sasabus.data.network.auth.jjwt.impl;

class Base64UrlCodec extends AbstractTextCodec {

    @Override
    public String encode(byte... data) {
        String base64Text = TextCodec.BASE64.encode(data);
        byte[] bytes = base64Text.getBytes(US_ASCII);

        //base64url encoding doesn't use padding chars:
        bytes = removePadding(bytes);

        //replace URL-unfriendly Base64 chars to url-friendly ones:
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == '+') {
                bytes[i] = '-';
            } else if (bytes[i] == '/') {
                bytes[i] = '_';
            }
        }

        return new String(bytes, US_ASCII);
    }

    private byte[] removePadding(byte... bytes) {

        byte[] result = bytes;

        int paddingCount = 0;
        for (int i = bytes.length - 1; i > 0; i--) {
            if (bytes[i] == '=') {
                paddingCount++;
            } else {
                break;
            }
        }
        if (paddingCount > 0) {
            result = new byte[bytes.length - paddingCount];
            System.arraycopy(bytes, 0, result, 0, bytes.length - paddingCount);
        }

        return result;
    }

    @Override
    public byte[] decode(String encoded) {
        char[] chars = encoded.toCharArray(); //always ASCII - one char == 1 byte

        //Base64 requires padding to be in place before decoding, so add it if necessary:
        chars = ensurePadding(chars);

        //Replace url-friendly chars back to normal Base64 chars:
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '-') {
                chars[i] = '+';
            } else if (chars[i] == '_') {
                chars[i] = '/';
            }
        }

        String base64Text = new String(chars);

        return TextCodec.BASE64.decode(base64Text);
    }

    private char[] ensurePadding(char... chars) {

        char[] result = chars; //assume argument in case no padding is necessary

        int paddingCount = 0;

        //fix for https://github.com/jwtk/jjwt/issues/31
        int remainder = chars.length % 4;
        if (remainder == 2 || remainder == 3) {
            paddingCount = 4 - remainder;
        }

        if (paddingCount > 0) {
            result = new char[chars.length + paddingCount];
            System.arraycopy(chars, 0, result, 0, chars.length);
            for (int i = 0; i < paddingCount; i++) {
                result[chars.length + i] = '=';
            }
        }

        return result;
    }
}
