package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.IOException;

public class BackupAutomatico {

    public static void iniciarBackupAutomatico() {
        int intervaloMinutos = 1;

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    // Hora do início
                    String inicio = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                    System.out.println("Backup automático iniciado às: " + inicio);

                    gerarBackupAutomatico();

                    // Hora do fim
                    String fim = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                    System.out.println("Backup automático concluído às: " + fim);

                    Thread.sleep(intervaloMinutos * 60 * 1000);

                } catch (InterruptedException e) {
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }


    // ✨ Backup automático
    private static void gerarBackupAutomatico() {
        try {
            String pasta = "C:\\Users\\timid\\Documents\\Backup\\";
            new File(pasta).mkdirs(); // cria se não existir

            String dataHora = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String caminhoFinal = pasta + "backup_auto_" + dataHora + ".sql";

            // ------ Reuso do BackupUtil ------
            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe",
                    "-u", "root",
                    "-p" + "root",
                    "soldadores"
            );

            pb.redirectOutput(new File(caminhoFinal));
            Process proc = pb.start();
            proc.waitFor();

        } catch (IOException | InterruptedException e) {
        }
    }
}
