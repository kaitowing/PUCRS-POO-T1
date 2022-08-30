import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    public static void editTrem(int opcao, int idTrem, PatioDeManobras patio, GaragemLocomotivas garagemL, GaragemVagoes garagemV){
        Scanner sc = new Scanner(System.in);
        Trem tremEdit = patio.getPorId(idTrem);
        switch(opcao){
            case 1:
                if (tremEdit == null){
                    System.out.println("Trem não existe.");
                }else{
                    if(tremEdit.getQtdadeVagoes() > 0){
                        System.out.println("Não é possível adicionar locomotiva atrás de um vagão.");
                    }else{
                        System.out.println("Digite o ID da locomotiva a ser adicionada");
                        int idLoc = Integer.parseInt(sc.nextLine());
                        tremEdit.engataLocomotiva(garagemL.getPorId(idLoc));
                    }
                }
            break;
            case 2:
                if (tremEdit == null){
                    System.out.println("Trem não existe.");
                }else{
                    System.out.println("Digite o ID do vagão a ser adicionado.");
                    int idVag = Integer.parseInt(sc.nextLine());
                    if(tremEdit.getQtdadeVagoes() >= tremEdit.maxVagoesNoTrem()
                    || tremEdit.pesoAtualDoTrem() + garagemV.getPorId(idVag).getCapacidadeCarga() > tremEdit.pesoMaxNoTrem()
                    ){
                        System.out.println("Não é possível adicionar um vagão neste trem.");
                    }else{
                        tremEdit.engataVagao(garagemV.getPorId(idVag));
                    }
                }
            break;
            case 3:
            if (tremEdit == null){
                System.out.println("Trem não existe.");
            }else{
                if(tremEdit.getQtdadeVagoes() == 0 && tremEdit.getQtdadeLocomotivas() <= 1){
                    System.out.println("Não é possível remover o último elemento deste trem.");
                }else{
                    if(tremEdit.getQtdadeVagoes() == 0 && tremEdit.getQtdadeLocomotivas() > 1){}
                }
            }
            break;
            case 4:
            break;
            case 5:
            break;
        }
    }
    public static void main(String[] args) throws Exception {

        GaragemLocomotivas garagemL = new GaragemLocomotivas();
        GaragemVagoes garagemV = new GaragemVagoes();
        PatioDeManobras patio = new PatioDeManobras();
    
        Scanner sc = new Scanner(System.in);

        writeL();
        writeV();

        carregaLocomotiva(garagemL);
        carregaVagao(garagemV);

        for (int i = 0; i < garagemV.qtdade(); i++) {
            System.out.println(garagemV.getPorPosicao(i).getIdentificador());
        }
        int pog = sc.nextInt();
        
        

        System.out.println("BEM VINDO AO SISTEMA DE LOCOMOTIVAS");
        System.out.println("DIGITE A OPÇÃO QUE DESEJA SELECIONAR!");
        System.out.println("1- CRIAR UM TREM");
        System.out.println("2- EDITAR UM TREM");
        System.out.println("3- LISTAR TODOS OS TRENS");
        System.out.println("4- DESFAZER UM TREM");
        System.out.println("5- ENCERRAR O PROGRAMA");

        int opcao = Integer.parseInt(sc.nextLine());

        switch(opcao){
            case 1:
                System.out.println("Informe o identificador do trem:");
                int idTrem = Integer.parseInt(sc.nextLine());
                System.out.println("Informe o identificador da primeira locomotiva:");
                int idLoc = Integer.parseInt(sc.nextLine());

                Trem novoTrem = new Trem(idTrem);
                
                Locomotiva primLoc = garagemL.getPorId(idLoc);

                novoTrem.engataLocomotiva(primLoc);

                patio.adicionaPatio(novoTrem);
            break;
            case 2:
                System.out.println("Informe o identificador do trem a ser editado:");
                idTrem = Integer.parseInt(sc.nextLine());
                System.out.println("Digite a opção de edição:");
                System.out.println("1- Inserir uma locomotiva");
                System.out.println("2- Inserir um vagão");
                System.out.println("3- Remover o último elemento do trem");
                System.out.println("4- Listar locomotivas livres");
                System.out.println("5- Listar vagões livres");
                System.out.println("6- Encerrar edição");
                int opcaoEdicao = Integer.parseInt(sc.nextLine());

                if(opcaoEdicao == 6){
                    break;
                }else{
                    
                }
            break;
            case 3:
            break;
            case 4:
            break;
            case 5:
            System.out.println("-- SAINDO DO SISTEMA --");
            break;
        }


    }

    public static void carregaLocomotiva(GaragemLocomotivas garagemL){
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+"\\"+"locomotiva.dat";
        Path path = Paths.get(nameComplete);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))){
            while (sc.hasNext()){
                String linha = sc.nextLine();

                String dados[] = linha.split(";");

                int identificador = Integer.parseInt(dados[0]);
                double pesoMaximo = Double.parseDouble(dados[1]);
                int qtdadeMaxVagoes = Integer.parseInt(dados[2]);
                Locomotiva l = new Locomotiva(identificador,pesoMaximo,qtdadeMaxVagoes);
                garagemL.adicionaGaragem(l);
            }
        }catch (IOException x){
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public static void carregaVagao(GaragemVagoes garagemV){
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+"\\"+"vagao.dat";
        Path path = Paths.get(nameComplete);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))){
            while (sc.hasNext()){
                String linha = sc.nextLine();

                String dados[] = linha.split(";");

                int identificador = Integer.parseInt(dados[0]);
                double capacidadeCarga = Double.parseDouble(dados[1]);
                Vagao v = new Vagao(identificador, capacidadeCarga);
                garagemV.adicionaGaragem(v);
            }
        }catch (IOException x){
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public static void writeL(){
        Random sorteia = new Random();
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+"\\"+"locomotiva.dat";
        Path path = Paths.get(nameComplete);

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))){
            for(int i=0;i<3;i++){
                int nro = sorteia.nextInt(100,200);
                writer.print(nro+";");
                nro = sorteia.nextInt(50,90);
                writer.print(nro+";");
                nro = sorteia.nextInt(10,20);
                writer.print(nro+"\n");
            }
        }catch (IOException x){
        System.err.format("Erro de E/S: %s%n", x);
        }
    }
    public static void writeV(){
        Random sorteia = new Random();
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+"\\"+"vagao.dat";
        Path path = Paths.get(nameComplete);

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))){
            for(int i=0;i<3;i++){
                int nro = sorteia.nextInt(100,200);
                writer.print(nro+";");
                nro = sorteia.nextInt(5,15);
                writer.print(nro+"\n");
            }
        }catch (IOException x){
        System.err.format("Erro de E/S: %s%n", x);
        }
    }
}