package com.edu.lx.cms.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.edu.lx.cms.domain.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Jwt工具类，生成JWT和认证
 */
public class JwtUtil {
    // 使用SLF4J日志框架创建一个Logger实例，用于记录日志信息
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // 从配置文件中加载的密钥，用于JWT的签名和验证
    private static final String secretKey = loadSecretKey();

    // 设置JWT的过期时间为604800秒（即1周）
//    private static final Long expiration = 1209600L;
    private static final Long expiration = 604800L;
    /**
     * 加载JWT使用的密钥。
     * <p>
     * 该方法从类路径下的配置文件`jwt.properties`中加载密钥。如果配置文件不存在或密钥未被正确指定，
     * 则抛出运行时异常，以确保应用程序在启动时能够正确配置密钥。
     *
     * @return 返回配置文件中指定的密钥字符串。
     * @throws RuntimeException 如果配置文件加载失败或密钥未被正确指定，抛出运行时异常。
     */
    private static String loadSecretKey() {
        // 创建Properties对象，用于存储配置文件中的键值对
        Properties properties = new Properties();
        try {
            // 使用JwtUtil类的类加载器加载配置文件jwt.properties
            // 这个方法会返回一个输入流，用于读取配置文件内容
            //这里的jwt.properties是密钥
            InputStream inputStream = JwtUtil.class.getClassLoader().getResourceAsStream("jwt.properties");

            // 检查输入流是否为null，如果是，则表示配置文件未找到
            if (inputStream == null) {
                // 抛出FileNotFoundException，表示配置文件未找到
                throw new FileNotFoundException("配置文件jwt.properties未找到");
            }

            // 使用Properties对象加载输入流中的配置信息
            properties.load(inputStream);

            // 从Properties对象中获取名为"secretKey"的属性值
            // 这个值是从配置文件中读取的密钥
            String secretKey = properties.getProperty("secretKey");

            // 检查获取到的密钥是否为空或null
            if (secretKey == null || secretKey.isEmpty()) {
                // 如果密钥未被正确指定，抛出IllegalArgumentException
                throw new IllegalArgumentException("配置文件中未指定secretKey");
            }

            // 如果一切正常，返回配置文件中指定的密钥
            return secretKey;
        } catch (FileNotFoundException e) {
            // 如果配置文件未找到，记录错误日志，并抛出运行时异常
            logger.error("配置文件jwt.properties未找到", e);
            throw new RuntimeException("配置文件加载失败，无法启动应用", e);
        } catch (IllegalArgumentException e) {
            // 如果配置文件中未指定密钥，记录错误日志，并抛出运行时异常
            logger.error("配置文件中未指定secretKey", e);
            throw new RuntimeException("配置文件加载失败，无法启动应用", e);
        } catch (Exception e) {
            // 捕获其他所有异常，记录错误日志，并抛出运行时异常
            // 这包括了Properties加载过程中可能发生的任何异常
            logger.error("密钥验证失败", e);
            throw new RuntimeException("密钥加载失败，无法启动应用", e);
        }
    }

    /**
     * 生成用户token，并设置token的超时时间。
     * 这个方法接受用户名作为参数，并创建一个包含用户名信息的JWT。
     * JWT的过期时间被设置为从生成时起的1周。
     *
     * @param user
     * @return 生成的JWT字符串
     */
    public static String createToken(User user) {
        // 检查用户名是否为空，避免创建无效的token
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            throw new IllegalArgumentException("账号不能为空");
        }

        // 设置JWT的过期时间为当前时间加上1周（1周的毫秒数）
        long twoWeeksInMilliseconds = 7 * 24 * 60 * 60 * 1000L;
        //创建了一个Date对象，表示从当前时间起加上一周后的日期和时间，这个日期和时间将被用作JWT的过期时间
        Date expireDate = new Date(System.currentTimeMillis() + twoWeeksInMilliseconds);

        // 创建一个Map来存储JWT头部信息
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256"); // 指定签名算法为HMAC256
        headerMap.put("typ", "JWT");   // 指定令牌类型为JWT

        // 使用Auth0的JWT库创建JWT
        // 首先创建一个JWT.builder()来配置JWT的各个部分
        JWTCreator.Builder builder = JWT.create()
                .withHeader(headerMap) // 添加头部信息
                .withClaim("userId", user.getUserId()) // 添加账号
                .withExpiresAt(expireDate) // 设置过期时间
                .withIssuedAt(new Date()) // 设置签发时间
                .withIssuer("LiXue"); // 可选：设置发行者
        //这里的 "yourIssuer" 是一个占位符，你应该替换为实际的发行者名称，例如你的应用或服务的名称

        // 检查secretKey是否已加载，避免签名时出现空指针异常
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("密钥未加载或为空");
        }

        // 使用密钥进行签名，并构建最终的JWT字符串
        String token ="Bearer " + builder.sign(Algorithm.HMAC256(secretKey));

        // 记录生成的JWT，便于调试和监控
        logger.info("Token created for user {}: {}", user.getUserId(), token);
        return token;
    }

    /**
     * 校验token并解析token。
     * 这个方法接受一个JWT字符串作为参数，并验证其有效性。
     * 如果token有效，返回true；如果token无效或过期，返回false。
     *
     * @param token 需要验证的JWT字符串
     * @return true如果token有效，false如果token无效或过期
     */
    public static boolean verifyToken(String token) {
        // 检查传入的token是否为空，如果为空直接返回false
        if (token == null || token.isEmpty()) {
            logger.error("令牌空值");
            return false;
        }

        try {
            // 使用Auth0的JWT库构建一个JWTVerifier实例，用于验证JWT
            // 这里假设secretKey是一个已经定义好的密钥，用于HMAC256算法
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();

            // 使用验证器验证JWT，如果token无效或被篡改，会抛出JWTVerificationException异常
            DecodedJWT jwt = verifier.verify(token);

            // 检查JWT是否过期，如果过期则返回false
            if (jwt.getExpiresAt().before(new Date())) {
                // 如果JWT过期，记录警告日志并返回false
                logger.warn("令牌已过期");
                return false;
            }

            // 如果JWT有效且未过期，返回true
            return true;
        } catch (JWTVerificationException e) {
            // 如果JWT验证失败，记录错误日志并返回false
            // 这可能发生在token无效、被篡改或签名不匹配时
            logger.error("令牌验证错误: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            // 如果发生其他异常，记录错误日志并返回false
            // 这可能是由于内部错误或配置问题导致的
            logger.error("令牌验证过程中出现意外错误", e);
            return false;
        }

    }
}