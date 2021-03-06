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

import it.sasabz.android.sasabus.data.network.auth.jjwt.lang.RuntimeEnvironment;

/**
 * Type-safe representation of standard JWT signature algorithm names as defined in the
 * <a href="https://tools.ietf.org/html/draft-ietf-jose-json-web-algorithms-31">JSON Web Algorithms</a> specification.
 *
 * @since 0.1
 */
public enum SignatureAlgorithm {

    /**
     * JWA algorithm name for {@code RSASSA-PKCS-v1_5 using SHA-256}
     */
    RS256("RS256", "RSASSA-PKCS-v1_5 using SHA-256", "RSA", "SHA256withRSA", true),

    /**
     * JWA algorithm name for {@code RSASSA-PKCS-v1_5 using SHA-384}
     */
    RS384("RS384", "RSASSA-PKCS-v1_5 using SHA-384", "RSA", "SHA384withRSA", true),

    /**
     * JWA algorithm name for {@code RSASSA-PKCS-v1_5 using SHA-512}
     */
    RS512("RS512", "RSASSA-PKCS-v1_5 using SHA-512", "RSA", "SHA512withRSA", true),

    /**
     * JWA algorithm name for {@code RSASSA-PSS using SHA-256 and MGF1 with SHA-256}.  <b>This is not a JDK standard
     * algorithm and requires that a JCA provider like BouncyCastle be in the runtime classpath.</b>  BouncyCastle
     * will be used automatically if found in the runtime classpath.
     */
    PS256("PS256", "RSASSA-PSS using SHA-256 and MGF1 with SHA-256", "RSA", "SHA256withRSAandMGF1", false),

    /**
     * JWA algorithm name for {@code RSASSA-PSS using SHA-384 and MGF1 with SHA-384}.  <b>This is not a JDK standard
     * algorithm and requires that a JCA provider like BouncyCastle be in the runtime classpath.</b>  BouncyCastle
     * will be used automatically if found in the runtime classpath.
     */
    PS384("PS384", "RSASSA-PSS using SHA-384 and MGF1 with SHA-384", "RSA", "SHA384withRSAandMGF1", false),

    /**
     * JWA algorithm name for {@code RSASSA-PSS using SHA-512 and MGF1 with SHA-512}. <b>This is not a JDK standard
     * algorithm and requires that a JCA provider like BouncyCastle be in the classpath.</b>  BouncyCastle will be used
     * automatically if found in the runtime classpath.
     */
    PS512("PS512", "RSASSA-PSS using SHA-512 and MGF1 with SHA-512", "RSA", "SHA512withRSAandMGF1", false);

    static {
        RuntimeEnvironment.enableBouncyCastleIfPossible();
    }

    private final String value;
    private final String description;
    private final String familyName;
    private final String jcaName;
    private final boolean jdkStandard;

    SignatureAlgorithm(String value, String description, String familyName, String jcaName, boolean jdkStandard) {
        this.value = value;
        this.description = description;
        this.familyName = familyName;
        this.jcaName = jcaName;
        this.jdkStandard = jdkStandard;
    }

    /**
     * Returns the JWA algorithm name constant.
     *
     * @return the JWA algorithm name constant.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the cryptographic family name of the signature algorithm.  The value returned is according to the
     * following table:
     * <p>
     * <table>
     * <thead>
     * <tr>
     * <th>SignatureAlgorithm</th>
     * <th>Family Name</th>
     * </tr>
     * </thead>
     * <tbody>
     * <tr>
     * <td>HS256</td>
     * <td>HMAC</td>
     * </tr>
     * <tr>
     * <td>HS384</td>
     * <td>HMAC</td>
     * </tr>
     * <tr>
     * <td>HS512</td>
     * <td>HMAC</td>
     * </tr>
     * <tr>
     * <td>RS256</td>
     * <td>RSA</td>
     * </tr>
     * <tr>
     * <td>RS384</td>
     * <td>RSA</td>
     * </tr>
     * <tr>
     * <td>RS512</td>
     * <td>RSA</td>
     * </tr>
     * <tr>
     * <td>PS256</td>
     * <td>RSA</td>
     * </tr>
     * <tr>
     * <td>PS384</td>
     * <td>RSA</td>
     * </tr>
     * <tr>
     * <td>PS512</td>
     * <td>RSA</td>
     * </tr>
     * <tr>
     * <td>ES256</td>
     * <td>Elliptic Curve</td>
     * </tr>
     * <tr>
     * <td>ES384</td>
     * <td>Elliptic Curve</td>
     * </tr>
     * <tr>
     * <td>ES512</td>
     * <td>Elliptic Curve</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @return Returns the cryptographic family name of the signature algorithm.
     * @since 0.5
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Returns the name of the JCA algorithm used to compute the signature.
     *
     * @return the name of the JCA algorithm used to compute the signature.
     */
    public String getJcaName() {
        return jcaName;
    }

    /**
     * Returns {@code true} if the algorithm is supported by standard JDK distributions or {@code false} if the
     * algorithm implementation is not in the JDK and must be provided by a separate runtime JCA Provider (like
     * BouncyCastle for example).
     *
     * @return {@code true} if the algorithm is supported by standard JDK distributions or {@code false} if the
     * algorithm implementation is not in the JDK and must be provided by a separate runtime JCA Provider (like
     * BouncyCastle for example).
     */
    public boolean isJdkStandard() {
        return jdkStandard;
    }

    /**
     * Returns {@code true} if the enum instance represents an RSA public/private key pair signature algorithm,
     * {@code false} otherwise.
     *
     * @return {@code true} if the enum instance represents an RSA public/private key pair signature algorithm,
     * {@code false} otherwise.
     */
    public boolean isRsa() {
        return description.startsWith("RSASSA");
    }

    /**
     * Looks up and returns the corresponding {@code SignatureAlgorithm} enum instance based on a
     * case-<em>insensitive</em> name comparison.
     *
     * @param value The case-insensitive name of the {@code SignatureAlgorithm} instance to return
     * @return the corresponding {@code SignatureAlgorithm} enum instance based on a
     * case-<em>insensitive</em> name comparison.
     * @throws SignatureException if the specified value does not match any {@code SignatureAlgorithm}
     *                            name.
     */
    public static SignatureAlgorithm forName(String value) throws SignatureException {
        for (SignatureAlgorithm alg : values()) {
            if (alg.value.equalsIgnoreCase(value)) {
                return alg;
            }
        }

        throw new SignatureException("Unsupported signature algorithm '" + value + '\'');
    }
}
