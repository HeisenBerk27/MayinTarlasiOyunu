package mayınTarlasıOyunu;

import java.util.Scanner;

public class MineSweeper {
    private int[][] tarla;
    private boolean[][] mayinlanmis;
    private int mayinSayisi;

    public MineSweeper(int satirSayisi, int sutunSayisi) {
        tarla = new int[satirSayisi][sutunSayisi];
        mayinlanmis = new boolean[satirSayisi][sutunSayisi];
        mayinSayisi = (satirSayisi * sutunSayisi) / 4; // one fourth of the cells are mines

        // randomly place the mines
        for (int i = 0; i < mayinSayisi; i++) {
            int satir, sutun;
            do {
                satir = (int) (Math.random() * satirSayisi);
                sutun = (int) (Math.random() * sutunSayisi);
            } while (tarla[satir][sutun] == -1); // don't place mines on top of each other
            tarla[satir][sutun] = -1; // mark as mine
        }

        // assign numbers to cells based on number of adjacent mines
        for (int satir = 0; satir < satirSayisi; satir++) {
            for (int sutun = 0; sutun < sutunSayisi; sutun++) {
                if (tarla[satir][sutun] == -1) {
                    continue; // skip mines
                }
                int komsuMayinSayisi = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue; // skip the cell itself
                        }
                        int komsuSatir = satir + i;
                        int komsuSutun = sutun + j;
                        if (komsuSatir >= 0 && komsuSatir < satirSayisi
                                && komsuSutun >= 0 && komsuSutun < sutunSayisi
                                && tarla[komsuSatir][komsuSutun] == -1) {
                            komsuMayinSayisi++;
                        }
                    }
                }
                tarla[satir][sutun] = komsuMayinSayisi;
            }
        }
    }

    public void basla() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // print the board
            System.out.print("  ");
            for (int sutun = 0; sutun < tarla[0].length; sutun++) {
                System.out.print(sutun + " ");
            }
            System.out.println();
            for (int satir = 0; satir < tarla.length; satir++) {
                System.out.print(satir + " ");
                for (int sutun = 0; sutun < tarla[satir].length; sutun++) {
                    if (mayinlanmis[satir][sutun]) {
                        System.out.print(tarla[satir][sutun] + " ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }

            // ask for input
            System.out.print("Satir ve sutun sayisini giriniz ");
            int satir = scanner.nextInt();
            int sutun = scanner.nextInt();

            // validate input
            if (satir < 0 || satir >= tarla.length || sutun < 0 || sutun >= tarla[0].length) {
                System.out.println("Geçersiz Giriş. Tekrar deneyin.");
                continue;
            }
            if (mayinlanmis[satir][sutun]) {
                System.out.println("Hücre zaten ortaya çıktı. Tekrar deneyin.");
                continue;
            }

            // reveal cell
            mayinlanmis[satir][sutun] = true;
            if (tarla[satir][sutun] == -1) {
                System.out.println("Oyun bitti! Bir mayına çarptın.");
                return;
            }
            if (isWin()) {
            	System.out.println("Tebrikler kazandın!");
                return;
            }

            // recursively reveal adjacent cells if the current cell has no adjacent mines
            if (tarla[satir][sutun] == 0) {
                bitisikHucreleriOrtayaCıkar(satir, sutun);
            }
        }
    }

    private void bitisikHucreleriOrtayaCıkar(int satir, int sutun) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int komsuSatir = satir + i;
                int komsuSutun = sutun + j;
                if (komsuSatir >= 0 && komsuSatir < tarla.length
                        && komsuSutun >= 0 && komsuSutun < tarla[0].length
                        && !mayinlanmis[komsuSatir][komsuSutun]) {
                    mayinlanmis[komsuSatir][komsuSutun] = true;
                    if (tarla[komsuSatir][komsuSutun] == 0) {
                        bitisikHucreleriOrtayaCıkar(komsuSatir, komsuSutun);
                    }
                }
            }
        }
    }

    private boolean isWin() {
        int hucreSayisi = tarla.length * tarla[0].length;
        int ortayaCıkanHucreSayisi = 0;
        for (int satir = 0; satir < tarla.length; satir++) {
            for (int sutun = 0; sutun < tarla[0].length; sutun++) {
                if (mayinlanmis[satir][sutun]) {
                    ortayaCıkanHucreSayisi++;
                }
            }
        }
        return ortayaCıkanHucreSayisi == hucreSayisi - mayinSayisi;
    }
}
