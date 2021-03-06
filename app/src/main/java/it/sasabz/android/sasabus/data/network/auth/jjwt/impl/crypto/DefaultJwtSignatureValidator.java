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

package it.sasabz.android.sasabus.data.network.auth.jjwt.impl.crypto;

import java.nio.charset.Charset;
import java.security.Key;

import it.sasabz.android.sasabus.data.network.auth.jjwt.SignatureAlgorithm;
import it.sasabz.android.sasabus.data.network.auth.jjwt.impl.TextCodec;
import it.sasabz.android.sasabus.data.network.auth.jjwt.lang.Assert;

public class DefaultJwtSignatureValidator implements JwtSignatureValidator {

    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    private final SignatureValidator signatureValidator;

    public DefaultJwtSignatureValidator(SignatureAlgorithm alg, Key key) {
        this(DefaultSignatureValidatorFactory.INSTANCE, alg, key);
    }

    private DefaultJwtSignatureValidator(SignatureValidatorFactory factory, SignatureAlgorithm alg, Key key) {
        Assert.notNull(factory, "SignerFactory argument cannot be null.");
        signatureValidator = factory.createSignatureValidator(alg, key);
    }

    @Override
    public boolean isValid(String jwtWithoutSignature, String base64UrlEncodedSignature) {
        byte[] data = jwtWithoutSignature.getBytes(US_ASCII);

        byte[] signature = TextCodec.BASE64URL.decode(base64UrlEncodedSignature);

        return signatureValidator.isValid(data, signature);
    }
}
