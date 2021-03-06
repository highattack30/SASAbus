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

package it.sasabz.android.sasabus.data.network.auth.jjwt;

import java.util.Map;

import it.sasabz.android.sasabus.data.network.auth.jjwt.impl.DefaultClaims;
import it.sasabz.android.sasabus.data.network.auth.jjwt.impl.DefaultHeader;
import it.sasabz.android.sasabus.data.network.auth.jjwt.impl.DefaultJwtBuilder;
import it.sasabz.android.sasabus.data.network.auth.jjwt.impl.DefaultJwtParser;

/**
 * Factory class useful for creating instances of JWT interfaces.  Using this factory class can be a good
 * alternative to tightly coupling your code to implementation classes.
 *
 * @since 0.1
 */
public final class Jwts {

    private Jwts() {
    }

    /**
     * Creates a new {@link Header} instance suitable for <em>plaintext</em> (not digitally signed) JWTs.
     *
     * @return a new {@link Header} instance suitable for <em>plaintext</em> (not digitally signed) JWTs.
     */
    public static Header header() {
        return new DefaultHeader();
    }

    /**
     * Creates a new {@link Header} instance suitable for <em>plaintext</em> (not digitally signed) JWTs, populated
     * with the specified name/value pairs.
     *
     * @return a new {@link Header} instance suitable for <em>plaintext</em> (not digitally signed) JWTs.
     */
    public static Header header(Map<String, Object> header) {
        return new DefaultHeader(header);
    }

    /**
     * Returns a new {@link Claims} instance to be used as a JWT body.
     *
     * @return a new {@link Claims} instance to be used as a JWT body.
     */
    public static Claims claims() {
        return new DefaultClaims();
    }

    /**
     * Returns a new {@link Claims} instance populated with the specified name/value pairs.
     *
     * @param claims the name/value pairs to populate the new Claims instance.
     * @return a new {@link Claims} instance populated with the specified name/value pairs.
     */
    public static Claims claims(Map<String, Object> claims) {
        return new DefaultClaims(claims);
    }

    /**
     * Returns a new {@link JwtParser} instance that can be configured and then used to parse JWT strings.
     *
     * @return a new {@link JwtParser} instance that can be configured and then used to parse JWT strings.
     */
    public static JwtParser parser() {
        return new DefaultJwtParser();
    }

    /**
     * Returns a new {@link JwtBuilder} instance that can be configured and then used to create JWT compact serialized
     * strings.
     *
     * @return a new {@link JwtBuilder} instance that can be configured and then used to create JWT compact serialized
     * strings.
     */
    public static JwtBuilder builder() {
        return new DefaultJwtBuilder();
    }
}