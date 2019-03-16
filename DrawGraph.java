
/**
 * @title   Program Pengecekkan Throughput ARQ pada Metode
 *          Send-n-Wait, Go-Back-N, dan Selective-Reject
 * 
 * @author  Origin   : Daniel Clark (https://github.com/dpclark4/Git_Test/blob/master/src/DrawGraph.java)
 * @author  Edit     : Muhamad Aldy B.
 * @version 0.9
 */


/**
 * Import semua library yang dibutuhkan, jika menggunakan IDE yang tepat
 * seperti VS Code maka saat memanggil fungsi Java dengan auto correct bisa
 * sekaligus memanggil library yang dibutuhkan.
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

@SuppressWarnings("serial")
public class DrawGraph extends JPanel {

   /**
     * Deklarasi variabel:
     * - penggunaan private static final merupaka KONSTANTA yang tidak bisa diubah
     *   -> MAX_SCORE         = nilai tertinggi dari axis Y
     *   -> PREF_W            = lebar (width) display saat memunculkan JFrame
     *   -> PREF_H            = tinggi (height) display saat memunculkan JFrame
     *   -> BORDER_GAP        = lebar antara grafik yang ditampilkan dengan maksimal display JFrame
     *   -> GRAPH_COLOR       = warna dasar dari display grafik, background color
     *   -> GRAPH_POINT_COLOR = warna dari titik-titik untuk display grafik
     *   -> GRAPH_STROKE      = mengambil objek untuk garis pada grafik
     *   -> GRAPH_POINT_WIDTH = lebar/diameter lingkaran dari titik-titik pada grafik
     *   -> Y_HATCH_CNT       = hatch pada axis Y
     */
   
   private static final double MAX_SCORE = 6.0;

   private static final int PREF_W = 400;
   private static final int PREF_H = 400;
   private static final int BORDER_GAP = 30;
   private static final Color GRAPH_COLOR = Color.green;
   private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
   private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
   private static final int GRAPH_POINT_WIDTH = 12;
   private static final int Y_HATCH_CNT = 10;

   private List<Double> scores;

   /**
     * Method ini bertujuan untuk memasukkan hasil data yang telah dikumpulkan untuk
     * dipetakan ke dalam grafik.
     * 
     * @param     List<Double> scores  mengambil parameter list untuk membuat array data
     */
   public DrawGraph(List<Double> scores) {
      this.scores = scores;
   }

   /**
     * Method ini bertujuan untuk mendesain grafik yang akan dibuat, di dalamnya terdapat
     * fungsi-fungsi untuk membuat titik-titik untuk grafik, garis grafik, lebar antar data,
     * X dan Y axis, dan komponen lainnya yang akan ditampilkan pada grafik.
     * 
     * @Override              meniban fungsi aslinya dengan method ini
     * @param    Grapichs g   mengambil parameter object Grapichs ke dalam method untuk membuat grafik
     */
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      // Grapichs2D adalah library yang dipakai untuk membuat grafik dalam layout 2 dimensi (X, Y)
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      // Mendefinisikan seberapa besar/luas layout dari grafik yang akan dibuat
      // -> xScale   = panjang layout
      // -> yScale   = tinggi layout
      double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (scores.size() - 1);
      double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SCORE - 1);

      // Fungsi yang membuat titik-titik yang diambil dari data sehingga bisa dipetakan
      List<Point> graphPoints = new ArrayList<Point>();

      // Dengan loop, titik-titik pada grafik dibuat satu per satu dari data yang ada
      // Masing-masing titik dibuat dengan mendefinisikan titik X dan Y nya
      for (int i = 0; i < scores.size(); i++) {
         int x1 = (int) (i * xScale + BORDER_GAP);
         int y1 = (int) ((MAX_SCORE - scores.get(i)) * yScale + BORDER_GAP);
         graphPoints.add(new Point(x1, y1));
      }

      // Setelah di definisikan dengan fungsi loop di atas, dibuat hasil pemetaan titiknya dengan drawLine
      g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
      g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

      // Dua fungsi di bawah ini bertujuan untuk membuat hatch marks, atau titik yang ada pada di garis tiap
      // axis sebagai penanda satuan/jarak dari data yang ada. Tujuannya untuk memberikan berapa banyak atau
      // besar data yang dipetakan ke dalam grafik.

      // Membuat hatch marks untuk Y axis, titik-titik pada samping grafik sebagai penanda seberapa besar datanya
      for (int i = 0; i < Y_HATCH_CNT; i++) {
         int x0 = BORDER_GAP;
         int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
         int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
         int y1 = y0;
         g2.drawLine(x0, y0, x1, y1);
      }

      // Membuat hatch marks untuk X axis, titik-titik pada samping grafik sebagai penanda seberapa besar datanya
      for (int i = 0; i < scores.size() - 1; i++) {
         int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (scores.size() - 1) + BORDER_GAP;
         int x1 = x0;
         int y0 = getHeight() - BORDER_GAP;
         int y1 = y0 - GRAPH_POINT_WIDTH;
         g2.drawLine(x0, y0, x1, y1);
      }

      // Dua fungsi di bawah ini bertujuan untuk memberikan warna pada komponen grafik. Adapun
      // kedua komponen tersebut yaitu titik-titik untuk menunjukkan data, dan garis-garis untuk
      // menunjukkan korelasi/hubungan satu titik dengan titik sebelum/sesudahnya.

      // Memberikan warna kepada garis-garis yang ada pada grafik
      Stroke oldStroke = g2.getStroke();
      g2.setColor(GRAPH_COLOR);
      g2.setStroke(GRAPH_STROKE);
      for (int i = 0; i < graphPoints.size() - 1; i++) {
         int x1 = graphPoints.get(i).x;
         int y1 = graphPoints.get(i).y;
         int x2 = graphPoints.get(i + 1).x;
         int y2 = graphPoints.get(i + 1).y;
         g2.drawLine(x1, y1, x2, y2);         
      }

      // Memberikan warna kepada titik-titk yang ada pada grafik
      g2.setStroke(oldStroke);      
      g2.setColor(GRAPH_POINT_COLOR);
      for (int i = 0; i < graphPoints.size(); i++) {
         int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
         int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
         int ovalW = GRAPH_POINT_WIDTH;
         int ovalH = GRAPH_POINT_WIDTH;
         g2.fillOval(x, y, ovalW, ovalH);
      }
   }

   /**
     * Method di bawah ini berfungsi untuk mengambil besar layout yang diinginkan sehingga grafik tidak
     * terlalu besar/kecil. Ukurannya diatur oleh kedua konstanta di bawah ini yaitu PREF_W dan PREF_H.
     * 
     * @return    new Dimension     object baru untuk pemetaan besar layout dengan panjang x lebar
     */
   @Override
   public Dimension getPreferredSize() {
      return new Dimension(PREF_W, PREF_H);
   }

   /**
     * Method di bawah ini berfungsi untuk membuat data yang akan dipetakan. Di dalamnya terdapat tiga
     * bentuk data yang akan dipetakan secara berderetan dengan fungsi JPanel. Data yang ditampilkan
     * yaitu throughput dari ARQ Protocol dengan tiga metode: 1. Ssr (Selective-Reject); 2. Sgbn
     * (Go-Back-N); dan 3. Ssw (Send-n-Wait)
     */
   private static void createAndShowGui() {
      
      // Mendefinisikan array list untuk tiga metode
      List<Double> scoresSsr = new ArrayList<Double>();
      List<Double> scoresSgbn = new ArrayList<Double>();
      List<Double> scoresSsw = new ArrayList<Double>();

      // Mendefinisikan komponen untuk menghitung throughput
      // -> n     = besar paket atau jumlah bit yang ada untuk dikirim, satuan bit
      // -> trd   = waktu delay saat paket berjalan (time-round delay), satuan detik (s)
      // -> v     = kecepatan/bandwidth dari media yang dipakai, satuan bit/detik (bps)
      // -> per   = rasio terjadinya request time out, atau packet error ratio (PRE)
      double n = 10000.0;
      double trd = 1.0;
      double v = 6000.0;
      double per = 0.01;

      // Variabel data merupakan penyimpanan sementara untuk hasil penghitungan yang akan di-
      // masukkan ke dalam array list
      double data = 0.0;

      for (int i = 1 ; i <= 100 ; i++) {
         data = (1.0 - per);
         data = (5.0 - (Math.log(data) * (-1.0))) + 1;
         if (per >= 1.00) {
            data = 1.00;
         }
         scoresSsr.add(data);
         per = per + 0.01;
      }

      // Untuk memetakan hasil array list dari Ssr dengan grafik
      DrawGraph mainPanelSsr = new DrawGraph(scoresSsr);
      per = 0.01;

      for (int i = 1 ; i <= 100 ; i++) {
         data = (n * (1 - per)) / (n + (per * trd * v));
         data = (5.0 - (Math.log(data) * (-1.0))) + 1;
         if (per >= 1.00) {
            data = 1.00;
         }
         scoresSgbn.add(data);
         per = per + 0.01;
      }

      // Untuk memetakan hasil array list dari Sgbn dengan grafik
      DrawGraph mainPanelSgbn = new DrawGraph(scoresSgbn);
      per = 0.01;

      for (int i = 1 ; i <= 100 ; i++) {
         data = (n * (1 - per)) / (n + (trd * v));
         data = (5.0 - (Math.log(data) * (-1.0))) + 1;
         if (per >= 1.00) {
            data = 1.00;
         }
         scoresSsw.add(data);
         per = per + 0.01;
      }

      // Untuk memetakan hasil array list dari Ssw dengan grafik
      DrawGraph mainPanelSsw = new DrawGraph(scoresSsw);

      // Fungsi di bawah ini dibagi menjadi dua: JFrame dan Jpanel
      // -> JFrame berfungsi untuk membuat layout dan grafik dari masing-masing data
      // -> JPanel berfungsi untuk membagi layar JFrame menjadi tiga bagian
      JFrame frame = new JFrame("DrawGraph");
      JPanel panel = new JPanel(new GridLayout(1,3));

      panel.add(mainPanelSsr);
      panel.add(mainPanelSgbn);
      panel.add(mainPanelSsw);
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(panel);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
   }

   /**
     * Fungsi main dari program yang ditujukan langsung memanggil method createAndShowGui()
     */
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}