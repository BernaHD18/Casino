import java.util.Random;
import java.util.Scanner;

public class main {

    static String[] pintas = {"Corazon", "Trebol", "Diamante", "Pica"};
    static int numeros[] = {1, 2, 3, 4, 5, 6, 7, 8,9, 10, 11, 12, 13};
    static Random rand = new Random();
    static Scanner teclado = new Scanner(System.in);

    public static void main (String [ ] args) {
        jugar();
    }

    public static void jugar(){
        String jugador[] = new String[2], dealer[] = new String[2];

        System.out.println("\n\t B L A C K   J A C K");
        System.out.println("Creando baraja...");
        String baraja[][] = crearBaraja();
        System.out.println("Barajando...");
        barajar(baraja);
        System.out.println("Repartiendo cartas...");
        repartir(baraja, jugador);
        repartir(baraja, dealer);
        System.out.println("\nJUEGO LISTO PARA INICIAR");


        int turno = 0, opc;
        while(true){
            mostrarMenu();
            if(turno == 0){
                System.out.println("Turno de dealer");
                opc = teclado.nextInt();
                if(opc == 1){
                    dealer = pedirCarta(baraja, dealer);
                    turno ++;
                }
                else if(opc == 2){
                    if(bajarse(jugador, dealer)){
                        break;
                    }
                }
                else if(opc == 3){
                    mostrarMano(dealer);
                }
            }
            else{
                System.out.println("Turno jugador");
                opc = teclado.nextInt();
                if(opc == 1){
                    turno ++;
                    jugador = pedirCarta(baraja, jugador);
                }
                else if(opc == 2){
                    if(bajarse(jugador, dealer)){
                        break;
                    }
                }
                else if(opc == 3){
                    mostrarMano(jugador);
                }
            }

            if(turno == 2){
                //reiniciar turnos
                turno = 0;
            }
        }
    }

    public static String[][] crearBaraja(){
        String baraja[][] = new String[4][13];
        for(int i=0; i<pintas.length; i++){
            for(int k=0; k<numeros.length; k++){
                baraja[i][k] = numeros[k]+" " + pintas[i];
            }
        }
        return baraja;
    }

    public static void barajar(String[][] baraja){
        for(int i=0; i<4; i++){
            for(int k=0; k<13; k++){
                int random1 = rand.nextInt(4);
                int random2 = rand.nextInt(13);
                String temp = baraja[random1][random2];
                baraja[random1][random2] = baraja[i][k];
                baraja[i][k] = temp;
            }
        }
    }

    //METODO PARA OBSERVAR LA BARAJA
    public static void mostrarBaraja(String[][] baraja){
        for(int i=0;i<4;i++){
            for(int k=0; k<13; k++){
                System.out.println(baraja[i][k]+ " ");
            }
        }
    }

    /*
    public static String[] crearMano(String[][] baraja) {
        String mano[] = new String[3];
        //Crear 3 cartas random inicialmente

        for(int i=0; i<3; i++){
            int random1 = rand.nextInt(4);
            int random2 = rand.nextInt(13);
            mano[i] = baraja[random1][random2];
        }
        return mano;
    }*/


    public static void repartir(String[][] baraja, String[] mano){
        //Crear 2 cartas random inicialmente
        for(int i=0; i<2; i++){
            int random1 = rand.nextInt(4);
            int random2 = rand.nextInt(13);
            mano[i] = baraja[random1][random2];
        }
    }

    public static String[] pedirCarta(String[][] baraja, String[] mano){
        String nuevaMano[] = new String[mano.length + 1];

        //copiar la mano a una nueva mano, para asi "agrandar" el vector para el jugador que pide la carta y agregar la carta
        for(int i=0; i< mano.length; i++){
            nuevaMano[i] = mano[i];
        }
        //Pedir carta de forma random
        int random1 = rand.nextInt(4);
        int random2 = rand.nextInt(13);

        nuevaMano[mano.length] = baraja[random1][random2];
        return nuevaMano;
    }

    //Metodo para mostrar las cartas que posee el jugador/dealer en la mano
    public static void mostrarMano(String[] mano){
        for(int i=0;i<mano.length; i++){
            System.out.print(mano[i]+ " ");
        }
    }

    public static Boolean esBlackJack(String[] mano){
        //VERIFICAR QUE EN SU MANO TENGA UN 10 Y UN 1 PARA DARLO COMO GANADOR INMEDIATAMENTE
        Boolean dies = false, uno = false;
        for(int i=0; i<mano.length; i++){
            String[] dividido = mano[i].split(" ");
            String tipo = dividido[1];
            int valor_carta = Integer.parseInt(dividido[0]);
            if(valor_carta == 10){
                dies = true;
            }
            else if(valor_carta == 1){
                uno = true;
            }
        }

        if(dies && uno){
            return true;
        }
        return false;
    }

    public static Boolean verificarGanador(String[] manoJugador, String[] manoDealer){
        int sumaJugador = 0;
        for(int i=0; i<manoJugador.length; i++) {
            String[] dividido = manoJugador[i].split(" ");
            String tipo = dividido[1];
            int valor_carta = Integer.parseInt(dividido[0]);
            sumaJugador += valor_carta;
        }
        int sumaDealer = 0;
        for(int i=0; i<manoDealer.length; i++) {
            String[] dividido = manoDealer[i].split(" ");
            String tipo = dividido[1];
            int valor_carta = Integer.parseInt(dividido[0]);
            sumaDealer += valor_carta;
        }
        System.out.println("Puntos jugador: "+ sumaJugador+" ");
        System.out.println("Puntos dealer: "+ sumaDealer+" ");

        if(sumaJugador > 21){
            System.out.println("Gana dealer");
            return true;
        }
        if(21 - sumaJugador < 21 - sumaDealer){
            System.out.println("Gana jugador");
            return true;
        }
        if(21 - sumaJugador >= 21 - sumaDealer){
            System.out.println("Gana dealer");
            return true;
        }
        return false;
    }

    public static Boolean bajarse(String[] manoJugador, String[] manoDealer){
        if(esBlackJack(manoJugador)){
            System.out.println("Jugador ha ganado por Blackjack");
        }
        else if(esBlackJack(manoDealer)){
            System.out.println("Dealer ha ganado por Blackjack");
        }

        //si ninguno gana por blackjack hay que verificar quien esta masa cerca de 21
        else{
            return verificarGanador(manoJugador, manoDealer);
        }
        return false;
    }
    public static void mostrarMenu(){
        //Los metodos crear mano, crearbaraja, verificar ganador y lo relacionado a procesos del juego se asume que no son accesibles por los jugadores
        System.out.println("\n1 - Pedir carta\n2 - Bajarse\n3 - Mostrar mano");
    }
}