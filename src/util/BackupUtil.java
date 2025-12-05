package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class BackupUtil {

    public static void gerarBackup() {
        // Tera que mudar para o pc da escola
        String mysqldumpPath = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe";

        // Seu banco
        String banco = "soldadores";

        // Usuário e senha
        String usuario = "root";
        String senha = "";

        // Gera a data e hora para o nome do arquivo
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss", new Locale("pt", "BR"));
        String dataHora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).format(formatter);

        // Nome padrão sugerido
        File nomeSugerido = new File("backup_soldadores_" + dataHora + ".sql");

        // Abrir janela para escolher onde salvar
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Escolha onde salvar o backup");
        chooser.setSelectedFile(nomeSugerido);

        int escolha = chooser.showSaveDialog(null);

        if (escolha != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Backup cancelado.");
            return;
        }

        File destinoArquivo = chooser.getSelectedFile();
        String destino = destinoArquivo.getAbsolutePath();

        // Caminho do log no Documents
        String logPath = System.getProperty("user.home") + "\\Documents\\backup_log.txt";

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    mysqldumpPath,
                    "-u", usuario,
                    "-p" + senha, 
                    banco
            );

            // redireciona o conteúdo para o arquivo .sql
            pb.redirectOutput(destinoArquivo);

            Process proc = pb.start();
            int result = proc.waitFor();

            if (result == 0) {
                registrarLog(logPath, "Backup criado com sucesso em: " + destino);
                JOptionPane.showMessageDialog(null, "Backup gerado com sucesso!");
            } else {
                registrarLog(logPath, "ERRO: mysqldump retornou código " + result);
                JOptionPane.showMessageDialog(null, "Erro ao gerar backup!");
            }

        } catch (Exception e) {
            registrarLog(logPath, "ERRO inesperado: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro inesperado ao gerar backup!");
        }
    }

    private static void registrarLog(String logPath, String mensagem) {
        try (FileWriter fw = new FileWriter(logPath, true)) {
            fw.write("[" + java.time.LocalDateTime.now() + "] " + mensagem + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao gravar log: " + e.getMessage());
        }
    }
}
