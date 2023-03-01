package mayınTarlasıOyunu;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Satır sayısını girin: ");
        int satirSayisi = scanner.nextInt();
        System.out.print("Sütun sayısını girin: ");
        int sutunSayisi = scanner.nextInt();
        MineSweeper oyun = new MineSweeper(satirSayisi, sutunSayisi);
        oyun.basla();
    }
}


