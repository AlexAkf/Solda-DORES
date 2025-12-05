package util;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *
 * @author Rafael Moreira
 */
public class RestaurarBackup {

    public static void restaurar() {

        String mysqlPath = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe";

        String banco = "soldadores";
        String usuario = "root";
        String senha   = "";

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecione o arquivo .SQL para restaurar");

        int escolha = chooser.showOpenDialog(null);

        if (escolha != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Restauração cancelada.");
            return;
        }

        File arquivoSQL = chooser.getSelectedFile();

        try {
            ProcessBuilder pb = new ProcessBuilder(
                mysqlPath,
                "-u", usuario,
                "-p" + senha,
                banco
            );

            pb.redirectInput(arquivoSQL);

            Process proc = pb.start();
            int result = proc.waitFor();

            if (result == 0) {
                JOptionPane.showMessageDialog(null, "Sistema restaurado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao restaurar! Código: " + result);
            }

        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
        }
    }
}