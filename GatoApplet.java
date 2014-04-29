import java.awt.*;
import java.applet.*;
import java.awt.event.*;

class Tablero{
    boolean usado;
    byte jugador,valor;
    Tablero(){
        this.usado=false;
        this.jugador=0;
        this.valor=0;
    }
}
class Juego{
    boolean terminado,empezado;
    int dificultad;
    String cadena;
    Juego(){
        this.terminado=false;
        this.empezado=false;
    }
    public boolean lleno(Tablero celda[][]){
        int i,j;
        boolean ver=true;
        for(j=0;j<3;j++){
            for(i=0;i<3;i++){
                if(celda[i][j].usado==false){
                    ver=false;
                }
            }
        }
        if(ver==true){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean verificar(Tablero celda[][],int jugador){
        boolean valor=true;
        valor=verlinea(celda,jugador,0,0,1,0,2,0);
        if(valor==false){
            valor=verlinea(celda,jugador,0,1,1,1,2,1);
        }
        if(valor==false){
            valor=verlinea(celda,jugador,0,2,1,2,2,2);  
        }
        if(valor==false){
            valor=verlinea(celda,jugador,0,0,0,1,0,2);  
        }
        if(valor==false){
            valor=verlinea(celda,jugador,1,0,1,1,1,2);  
        }
        if(valor==false){
            valor=verlinea(celda,jugador,2,0,2,1,2,2);  
        }
        if(valor==false){
            valor=verlinea(celda,jugador,0,0,1,1,2,2);  
        }
        if(valor==false){
            valor=verlinea(celda,jugador,2,0,1,1,0,2);
        }
        return valor;
    } 
    public boolean verlinea(Tablero celda[][],int jugador,int x1,int y1,int x2,int y2,int x3,int y3){
        boolean valor=true;
        
        
        if(celda[x1][y1].jugador==jugador && celda[x2][y2].jugador==jugador && celda[x3][y3].jugador==jugador){
            valor=true;
        }
        else{
            valor=false;
        }
        return valor;
       
    }
}
class Enemigo{
    Enemigo(Tablero celda[][]){
        int i,j;
        for(j=0;j<3;j++){
            for(i=0;i<3;i++){
                if((i==0 || i==2)&&(j==0 || j==2)){
                    celda[i][j].valor=3;
                }
                else{
                    celda[i][j].valor=2;
                }
            }
            celda[1][1].valor=4;
        }
    }
    public void mover(Tablero celda[][]){
        int movx=0;
        int movy=0;
        int i,j;
        int max=0;
        for(j=0;j<3;j++){
            for(i=0;i<3;i++){
                if(celda[i][j].valor>=max){
                    max=celda[i][j].valor;
                    movx=i;
                    movy=j;
                }
            }
        }
        celda[movx][movy].usado=true;
        celda[movx][movy].jugador=2;
        celda[movx][movy].valor=-1;
    }
    public void calc(Tablero celda[][],Juego gato){
        this.linea(celda,0,1,2,0,0,0);
        this.linea(celda,0,1,2,1,1,1);
        this.linea(celda,0,1,2,2,2,2);
        this.linea(celda,0,0,0,0,1,2);
        this.linea(celda,1,1,1,0,1,2);
        this.linea(celda,2,2,2,0,1,2);
        this.linea(celda,0,1,2,0,1,2);
        this.linea(celda,0,1,2,2,1,0);
        if(gato.dificultad>=2){
            if(celda[0][0].jugador==1 && celda[2][2].jugador==1 && celda[1][1].jugador==2 && celda[1][0].usado==false){
                celda[1][0].valor=4;
            }
            if(celda[2][0].jugador==1 && celda[0][2].jugador==1 && celda[1][1].jugador==2 && celda[1][2].usado==false){
                celda[1][2].valor=4;
            }
        }
        if(gato.dificultad>=3){
            this.dif3(celda,0,1,1,0,0,0);
            this.dif3(celda,1,0,2,1,2,0);
            this.dif3(celda,0,1,1,2,0,2);
            this.dif3(celda,2,1,1,2,2,2);
            
            this.dif3(celda,0,1,2,0,0,0);
            this.dif3(celda,2,1,0,0,2,0);
            this.dif3(celda,0,1,2,2,0,2);
            this.dif3(celda,0,2,2,1,2,2);
            
            this.dif3(celda,1,0,2,2,2,0);
            this.dif3(celda,1,0,0,2,0,0);
            this.dif3(celda,1,2,0,0,0,2);
            this.dif3(celda,1,2,2,0,2,2);
        }   
    }
    public void dif3(Tablero celda[][],int x1,int y1,int x2,int y2,int cx,int cy){
        if(celda[x1][y1].jugador==1 && celda[x2][y2].jugador==1 && celda[cx][cy].usado==false){
            celda[cx][cy].valor=4;
        }
    }
    public void linea(Tablero celda[][],int x1,int x2,int x3,int y1,int y2,int y3){
        if(celda[x1][y1].jugador==1 && celda[x2][y2].jugador==1 && celda[x3][y3].usado==false){
            celda[x3][y3].valor=5;
        }
        if(celda[x1][y1].jugador==1 && celda[x2][y2].usado==false && celda[x3][y3].jugador==1){
            celda[x2][y2].valor=5;
        }
        if(celda[x1][y1].usado==false && celda[x2][y2].jugador==1 && celda[x3][y3].jugador==1){
            celda[x1][y1].valor=5;
        }
        this.ganar(celda,x1,x2,x3,y1,y2,y3);
    }
    public void ganar(Tablero celda[][],int x1,int x2,int x3,int y1,int y2,int y3){
        if(celda[x1][y1].jugador==2 && celda[x2][y2].jugador==2 && celda[x3][y3].usado==false){
            celda[x3][y3].valor=6;
        }
        if(celda[x1][y1].jugador==2 && celda[x2][y2].usado==false && celda[x3][y3].jugador==2){
            celda[x2][y2].valor=6;
        }
        if(celda[x1][y1].usado==false && celda[x2][y2].jugador==2 && celda[x3][y3].jugador==2){
            celda[x1][y1].valor=6;
        }
    }
}
public class GatoApplet extends Applet implements MouseListener,ActionListener,Runnable{
    public Tablero celda[][]=new Tablero[3][3];
    public Enemigo enemigo;
    public Juego gato;
    Button facil,medio,dificil,reiniciar;
    boolean turno;
    boolean salir;
    public void init(){
        setBackground(Color.blue);
        setForeground(Color.black);
        facil=new Button("Facil");
        medio=new Button("Medio");
        dificil=new Button("Dificil");
        addMouseListener(this);
        facil.addActionListener(this);
        medio.addActionListener(this);
        dificil.addActionListener(this);
        reiniciar();
        add(facil);
        add(medio);
        add(dificil);
        salir=false;
        Thread t=new Thread(this,"Hilo");
        t.start();
    }
    public void stop(){
        salir=true;
    }
    public void run(){
        byte num=1;
        boolean desp=false;
        boolean tempusado;
        byte tempjugador,i,j,cont;
        cont=0;
        for(;;){
            if(gato.terminado==true && desp==false){
                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException e){}
                if(num!=0){
                    num--;
                }
                else if(num==0){
                    num=1;
                    desp=true;
                }
            }
            
            if(gato.terminado==true && desp==true){
                try{
                    Thread.sleep(100);
                }
                catch(InterruptedException e){}
                
                tempusado=celda[0][0].usado;
                tempjugador=celda[0][0].jugador;
                for(j=0;j<3;j++){
                    for(i=0;i<3;i++){
                        if(i<2){
                            celda[i][j].usado=celda[i+1][j].usado;
                            celda[i][j].jugador=celda[i+1][j].jugador;
                        }
                        if(i==2 && j!=2){
                            celda[i][j].usado=celda[0][j+1].usado;
                            celda[i][j].jugador=celda[0][j+1].jugador;
                        }
                    }
                }
                celda[2][2].usado=tempusado;
                celda[2][2].jugador=tempjugador;
                cont++;
                repaint();
                if(cont==18){
                    cont=0;
                    reiniciar();
                    desp=false;
                }
            }
            if(salir==true){
                break;
            }
        }
    }
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource()==facil || evt.getSource()==medio || evt.getSource()==dificil){
            if(evt.getSource()==facil){
                gato.dificultad=1;
            }
            else if(evt.getSource()==medio){
                gato.dificultad=2;
            }
            else if(evt.getSource()==dificil){
                gato.dificultad=3;
            }
            remove(facil);
            remove(medio);
            remove(dificil);
            gato.empezado=true;
            setForeground(Color.white);
            repaint();
            String dif;
            if(gato.dificultad==1){
                dif="Facil";
            }
            else if(gato.dificultad==2){
                dif="Medio";
            }
            else{
                dif="Dificil";
            }
            showStatus("Dificultad = "+dif);
        }
    }
    public void mouseClicked(MouseEvent evt){
        int x,y;
        x=(evt.getX())/50;
        y=(evt.getY())/50;
        if(turno==true && gato.empezado==true && gato.terminado==false){
            if(celda[x][y].usado==false){
                celda[x][y].usado=true;
                celda[x][y].jugador=1;
                celda[x][y].valor=-1;
                turno=false;
                if(gato.verificar(celda,1)){
                    gato.terminado=true;
                    showStatus("GANASTE!!");
                    gato.cadena="GANASTE!! ";
                }
                if(gato.lleno(celda) && gato.terminado==false){
                    gato.terminado=true;
                    showStatus("EMPATE!!");
                    gato.cadena="EMPATE!! ";
                }
                if(gato.terminado==false){
                    enemigo.calc(celda,gato);
                    enemigo.mover(celda);
                    turno=true;
                    if(gato.verificar(celda,2)){
                        gato.terminado=true;
                        showStatus("PERDISTE!!");
                        gato.cadena="PERDISTE!! =( ";
                    }   
                    if(gato.lleno(celda) && gato.terminado==false){
                        gato.terminado=true;
                        showStatus("EMPATE!!");
                        gato.cadena="EMPATE!! ";
                    }
                }
            }
            repaint();
        }
    }
    public void mouseExited(MouseEvent evt){
        
    }
    public void mouseEntered(MouseEvent evt){
        
    }
    public void mouseReleased(MouseEvent evt){
        
    }
    public void mousePressed(MouseEvent evt){
        
    }
    public void paint(Graphics g){
        if(gato.empezado==true && gato.terminado==true){
            try{
                g.drawString(gato.cadena,50,65);
            }
            catch(NullPointerException e){}
        }   
        if(gato.empezado==true){
            String caracter="";
            g.drawLine(50,5,50,145);
            g.drawLine(100,5,100,145);
            g.drawLine(5,50,145,50);
            g.drawLine(5,100,145,100);
            int x,y;
            for(y=0;y<3;y++){
                for(x=0;x<3;x++){
                    if(celda[x][y].usado==true){
                        if(celda[x][y].jugador==1){
                            caracter="X";
                        }
                        if(celda[x][y].jugador==2){
                            caracter="O";
                        }
                        try{
                            g.drawString(caracter,x*50+20,y*50+30);
                        }
                        catch(NullPointerException e){}
                    }
                }
            }
        }   
        else if(gato.empezado==false && gato.terminado==false){
            try{
                g.drawString("Selecciona la dificultad",10,50);
            }
            catch(NullPointerException e){}
        }
    }
    public void reiniciar(){
        byte i,j;
        turno=true;
        for(i=0;i<3;i++){
            for(j=0;j<3;j++){
                celda[j][i]=new Tablero();
            }
        }
        setForeground(Color.black);
        add(facil);
        add(medio);
        add(dificil);
        enemigo=new Enemigo(celda);
        gato=new Juego();
        repaint();
    }
}