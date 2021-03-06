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

/**
 * Base class for JWT-related runtime exceptions.
 *
 * @since 0.1
 */
class JwtException extends RuntimeException {

    private static final long serialVersionUID = 6949451791970082995L;

    JwtException(String message) {
        super(message);
    }

    JwtException(String message, Throwable cause) {
        super(message, cause);
    }
}