# MayinTarlasiOyunu
www.patika.dev
------


import java.util.Scanner;

public class MineSweeper {

     int[][] tarla;
     boolean[][] acilmisHucre;
     int mayinSayisi;

    public MineSweeper(int satirSayisi, int sutunSayisi) {
        tarla = new int[satirSayisi][sutunSayisi];
        acilmisHucre = new boolean[satirSayisi][sutunSayisi];
        mayinSayisi = (satirSayisi * sutunSayisi) / 4; 

        
        for (int i = 0; i < mayinSayisi; i++) {
            int satir, sutun;
            do {
                satir = (int) (Math.random() * satirSayisi);
                sutun = (int) (Math.random() * sutunSayisi);
            } while (tarla[satir][sutun] == -1); 
            tarla[satir][sutun] = -1; 
        }

        
        for (int satir = 0; satir < satirSayisi; satir++) {
            for (int sutun = 0; sutun < sutunSayisi; sutun++) {
                if (tarla[satir][sutun] == -1) {
                    continue; 
                }
                int komsuMayinSayisi = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue; 
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
            
            System.out.print("  ");
            for (int sutun = 0; sutun < tarla[0].length; sutun++) {
                System.out.print(sutun + " ");
            }
            System.out.println();
            for (int satir = 0; satir < tarla.length; satir++) {
                System.out.print(satir + " ");
                for (int sutun = 0; sutun < tarla[satir].length; sutun++) {
                    if (acilmisHucre[satir][sutun]) {
                        System.out.print(tarla[satir][sutun] + " ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }

            
            System.out.print("Satir ve sutun sayisini giriniz ");
            int satir = scanner.nextInt();
            int sutun = scanner.nextInt();

            
            if (satir < 0 || satir >= tarla.length || sutun < 0 || sutun >= tarla[0].length) {
                System.out.println("Ge??ersiz Giri??. Tekrar deneyin.");
                continue;
            }
            if (acilmisHucre[satir][sutun]) {
                System.out.println("H??cre zaten ortaya ????kt??. Tekrar deneyin.");
                continue;
            }

            
            acilmisHucre[satir][sutun] = true;
            if (tarla[satir][sutun] == -1) {
                System.out.println("Oyun bitti! Bir may??na ??arpt??n.");
                return;
            }
            if (isWin()) {
            	System.out.println("Tebrikler kazand??n!");
                return;
            }

            
            if (tarla[satir][sutun] == 0) {
                bitisikHucreleriOrtayaC??kar(satir, sutun);
            }
        }
    }

    private void bitisikHucreleriOrtayaC??kar(int satir, int sutun) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int komsuSatir = satir + i;
                int komsuSutun = sutun + j;
                if (komsuSatir >= 0 && komsuSatir < tarla.length
                        && komsuSutun >= 0 && komsuSutun < tarla[0].length
                        && !acilmisHucre[komsuSatir][komsuSutun]) {
                    acilmisHucre[komsuSatir][komsuSutun] = true;
                    if (tarla[komsuSatir][komsuSutun] == 0) {
                        bitisikHucreleriOrtayaC??kar(komsuSatir, komsuSutun);
                    }
                }
            }
        }
    }

    private boolean isWin() {
        int hucreSayisi = tarla.length * tarla[0].length;
        int ortayaC??kanHucreSayisi = 0;
        for (int satir = 0; satir < tarla.length; satir++) {
            for (int sutun = 0; sutun < tarla[0].length; sutun++) {
                if (acilmisHucre[satir][sutun]) {
                    ortayaC??kanHucreSayisi++;
                }
            }
        }
        return ortayaC??kanHucreSayisi == hucreSayisi - mayinSayisi;
    }
}

-------


import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Sat??r say??s??n?? girin: ");
        int satirSayisi = scanner.nextInt();
        System.out.print("S??tun say??s??n?? girin: ");
        int sutunSayisi = scanner.nextInt();
        MineSweeper oyun = new MineSweeper(satirSayisi, sutunSayisi);
        oyun.basla();
    }
}
