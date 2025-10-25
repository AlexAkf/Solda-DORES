/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author ALUNO
 */
// Criando painel com degradê:
public class Gradiente extends JPanel {
    public Gradiente() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        Color cor1 = new Color(20, 38, 91);
        Color cor2 = new Color(30, 58, 138);
        GradientPaint gp = new GradientPaint(0, 0, cor1, 180, height, cor2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
    }
}
