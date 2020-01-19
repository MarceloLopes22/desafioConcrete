package com.desafio.concrete.utils;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.desafio.concrete.entidades.User;

@Component
public class Util {
	
	public void gerarUuid(User user) {
	    String uuid = UUID.randomUUID().toString().substring(0,16);
	    SecretKeySpec key = new SecretKeySpec(uuid.getBytes(), "AES");
	    
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);	 	 
			byte[] messageEncriptada = cipher.doFinal(key.toString().getBytes());	 	 
			String token = Base64.getEncoder().encodeToString(messageEncriptada);
			user.setToken(token);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}

	public void encriptyPassword(Object object) {
		User user = null;
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			user = User.class.cast(object);
			m.update(user.getPassword().getBytes(), 0, user.getPassword().length());
			byte[] digest = m.digest();
			String hexa = new BigInteger(1,digest).toString(16);
			user.setPassword(hexa);
		} catch (NoSuchAlgorithmException e) {
		    e.printStackTrace();
		}
	}
}
