package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe responsável pela criação do hash das senhas das contas para evitar
 * que o banco guarde as senhas em texto puro.
 *
 * @author Alex
 */

public class Hash {
    
    /**
     * Gera o hash SHA-256 de uma senha.
     *
     * @param senha A senha em texto cru
     * @return O hash da senha em formato hexadecimal
     */
    public static String gerarHash(String senha) {
        try {
            // Cria um objeto MessageDigest para o algoritmo SHA-256
            var digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());    // Gera o hash

            // Converte os bytes do hash para uma string hexadecimal
            var conversor = new StringBuilder();
            for (byte baite : hash) {
                conversor.append(String.format("%02x", baite));
            }

            return conversor.toString();  // Retorna o hash em formato hexadecimal
        } catch (NoSuchAlgorithmException e) {
            return null;  // Retorna null se ocorrer um erro ao gerar o hash
        }
    }
}