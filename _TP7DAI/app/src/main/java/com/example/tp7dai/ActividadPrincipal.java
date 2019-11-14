package com.example.tp7dai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.menus.Menu;
import org.cocos2d.menus.MenuItemImage;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Queue;
import java.util.Random;

public class ActividadPrincipal extends Activity {

    CCGLSurfaceView VistaPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.actividad_principal);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        VistaPrincipal = new CCGLSurfaceView(this);
        setContentView(VistaPrincipal);
    }

    @Override
    protected void onStart() {
        super.onStart();
        clsJuego MiGenialJuego;
        MiGenialJuego = new clsJuego(VistaPrincipal);
        MiGenialJuego.ComenzarJuego();
    }


    public class clsJuego{
        CCGLSurfaceView _VistaDelJuego;
        CCSize _Pantalla;
        Sprite _Jugador;
        Sprite _Enemigo;
        Sprite _ImagenFondo;
        boolean _EstaTocandoAlJugador;
        boolean _EstaTocandoAlEnemigo;

        public  clsJuego(CCGLSurfaceView VistaDelJuego){
            Log.d("Bob", "Comienza el constructor de la clase");
            _VistaDelJuego = VistaDelJuego;
        }
        public void ComenzarJuego(){
            Log.d("ComenzarJuego", "Comienza el juego");
            Director.sharedDirector().attachInView(_VistaDelJuego);

            _Pantalla = Director.sharedDirector().displaySize();
            Log.d("ComenzarJuego", "Pantalla - Ancho: "+ _Pantalla.getWidth()+" - Alto: "+_Pantalla.getHeight());


            Log.d("ComenzarJuego", "Declaro e instancio la escena");
            Scene escenaAUsar;
            escenaAUsar = EscenaBienvenida();

            Log.d("ComenzarJuego", "Le digo al director que inicie la escena");
            Director.sharedDirector().runWithScene(escenaAUsar);
        }


        private Scene EscenaBienvenida(){
            Log.d("EscenaComienzo", "Comienza");
            Scene escenaADevolver;
            escenaADevolver = Scene.node();

            Log.d("EscenaComienzo", "Voy a agregar la capa");
            capaBienvenida unaCapa;
            unaCapa = new capaBienvenida();
            escenaADevolver.addChild(unaCapa);

            Log.d("EscenaComienzo", "Devuelvo la escena creada");
            return escenaADevolver;
        }

        private Scene EscenaComienzo(){
            Log.d("EscenaComienzo", "Comienza");
            Scene escenaADevolver;
            escenaADevolver = Scene.node();

            Log.d("EscenaComienzo", "Voy a agregar la capa");
            capaJuego unaCapa;
            unaCapa = new capaJuego();
            escenaADevolver.addChild(unaCapa);

            Log.d("EscenaComienzo", "Devuelvo la escena creada");
            return escenaADevolver;
        }

        private Scene EscenaGameOver(){
            Log.d("EscenaComienzo", "Comienza");
            Scene escenaADevolver;
            escenaADevolver = Scene.node();

            Log.d("EscenaComienzo", "Voy a agregar la capa");
            capaJuego unaCapa;
            unaCapa = new capaJuego();
            escenaADevolver.addChild(unaCapa);

            Log.d("EscenaComienzo", "Devuelvo la escena creada");
            return escenaADevolver;
        }

        class capaBienvenida extends Layer {

            public capaBienvenida(){
                Log.d("CapaJuego", "Comienza el constructor");

                Log.d("CapaJuego", "Voy a ubicar la imagen de fondo");
                ponerImagenFondo();

                Log.d("CapaJuego", "Voy a ubicar los cuadrados");
                ponerCuadradosArriba();

                Log.d("CapaJuego", "Voy a ubicar el titulo");
                Titulo();

                Log.d("CapaJuego", "Voy a ubicar el boton");
                PonerBotones();

                Log.d("CapaJuego", "habilito el touch");
                setIsTouchEnabled(true);
            }


            void ponerCuadradosArriba(){
                Sprite CuadradosFondo;
                Log.d("PonerCuadrados", "Le asigno la imagen grafica al Sprite del jugador");
                CuadradosFondo = Sprite.sprite("cuadradosfondo.jpg");

                Log.d("PonerCuadrados", "Lo ubico");
                float factorAncho;
                factorAncho = _Pantalla.getWidth()/CuadradosFondo.getWidth();

                Log.d("PonerCuadrados", "ubico el sprite");
                CuadradosFondo.setPosition(_Pantalla.getWidth()/2,_Pantalla.getHeight()- (CuadradosFondo.getHeight()*factorAncho)/2);

                Log.d("PonerFondo", "Lo escalo para que ocupe todo el ancho de pantalla y se escale la altura en su misma proporcion");
                CuadradosFondo.runAction(ScaleBy.action(0.01f,factorAncho,factorAncho));

                Log.d("PonerJugador", "Lo agrego a la capa");
                super.addChild(CuadradosFondo,10);
            }


            void ponerImagenFondo() {
                Log.d("PonerFondo", "Le asigno la imagen grafica al Sprite del fondo");
                _ImagenFondo = Sprite.sprite("fondo.jpg");

                Log.d("PonerFondo", "Lo ubico");
                float factorAncho,factorAlto;
                factorAncho = _Pantalla.getWidth()/_ImagenFondo.getWidth();
                factorAlto = _Pantalla.getHeight()/_ImagenFondo.getHeight();
                _ImagenFondo.setPosition(_Pantalla.getWidth()/2,_Pantalla.getHeight()/2);

                Log.d("PonerFondo", "Le escalo para que ocupe toda la pantalla");
                _ImagenFondo.runAction(ScaleBy.action(0.01f,factorAncho,factorAlto));

                Log.d("PonerFondo", "Lo agrego a la capa");
                super.addChild(_ImagenFondo,-10);
            }



            void Titulo() {
                Sprite Titulo;
                Log.d("PonerTitulo", "Voy a armar el sprite del enemigo");
                Titulo = Sprite.sprite("titulo.jpg");

                Log.d("PonerTitulo", "Lo ubico");
                float factorAncho;
                factorAncho = _Pantalla.getWidth()/Titulo.getWidth();

                Log.d("PonerTitulo", "ubico el sprite");
                Titulo.setPosition(_Pantalla.getWidth()/2,_Pantalla.getHeight()/2);

                Log.d("PonerTitulo", "Lo escalo para que ocupe todo el ancho de pantalla y se escale la altura en su misma proporcion");
                Titulo.runAction(ScaleBy.action(0.01f,factorAncho,factorAncho));

                Log.d("PonerTitulo", "lo agrego a la capa");
                super.addChild(Titulo, 10);

            }

            void PonerBotones() {
                float factor;
                Log.d("PonerBotones", "Voy a crear el boton de jugar");
                MenuItemImage BotonJugar;
                BotonJugar = MenuItemImage.item("botonjugar.jpg","botonjugar.jpg",this, "PresionarBotonJuego");


                Log.d("PonerBotones", "Lo ubico");
                factor = 3;
                BotonJugar.setPosition(_Pantalla.getWidth()/2,(_Pantalla.getHeight()/2)-500);

                Log.d("PonerBotones", "Lo escalo para que ocupe todo el ancho de pantalla y se escale la altura en su misma proporcion");
                BotonJugar.runAction(ScaleBy.action(0.01f,factor,factor));

                Log.d("PonerBotones", "armo el menu con el boton");
                Menu menuDeBotones;
                menuDeBotones = Menu.menu(BotonJugar);
                menuDeBotones.setPosition(0,0);
                super.addChild(menuDeBotones);

            }


            public void PresionarBotonJuego(){
                Log.d("BotonJugar", "Se presiono el boton jugar");

                Log.d("BotonJugar", "Cambio de escena");

                Log.d("BotonJugar", "Declaro e instancio la escena");
                Scene escenaAUsar;
                escenaAUsar = EscenaComienzo();

                Log.d("BotonJugar", "Le digo al director que inicie la escena");
                Director.sharedDirector().replaceScene(escenaAUsar);
            }

        }

        class capaJuego extends Layer {

            public capaJuego(){
                Log.d("CapaJuego", "Comienza el constructor");

                Log.d("CapaJuego", "Voy a ubicar el jugador en su poscicion inicial");
                ponerJugador();

                Log.d("CapaJuego", "Voy a ubicar la imagen de fondo");
                ponerImagenFondo();

                Log.d("CapaJuego", "Voy a ubicar el enemigo");
                ponerUneEnemigo();

                Log.d("CapaJuego", "inicio el verificador de colisiones");
                super.schedule("detectarColisiones", 0.25f);

                Log.d("CapaJuego", "habilito el touch");
                setIsTouchEnabled(true);
            }

            // me falta que aparezca en una posicion random
            void ponerJugador(){
                Log.d("PonerJugador", "Le asigno la imagen grafica al Sprite del jugador");
                _Jugador = Sprite.sprite("jugador.png");

                Log.d("PonerJugador", "declaro la posicion incial");
                CCPoint posicionInicial;
                posicionInicial = new CCPoint();

                Log.d("PonerJugador", "determino la posicion x al azar");
                Random generadorDeAzar;
                generadorDeAzar = new Random();
                float anchoJugador;
                anchoJugador = _Jugador.getWidth();
                posicionInicial.x = generadorDeAzar.nextInt((int)(_Pantalla.getWidth()-anchoJugador));
                posicionInicial.x += anchoJugador/2;


                Log.d("PonerJugador", "determino la posicion y al azar");
                Random generadorDeAzar2;
                generadorDeAzar2 = new Random();
                float altoJugador;
                altoJugador = _Jugador.getHeight();
                posicionInicial.y = generadorDeAzar2.nextInt((int)(_Pantalla.getHeight()-altoJugador));
                posicionInicial.y += altoJugador/2;

                Log.d("PonerEnemigo", "ubico el sprite");
                _Jugador.setPosition(posicionInicial.x,posicionInicial.y);

                Log.d("PonerJugador", "Lo agrego a la capa");
                super.addChild(_Jugador,10);
            }


            void ponerImagenFondo() {
                Log.d("PonerFondo", "Lo agrego a la capa");
                super.addChild(_ImagenFondo,-10);
            }


            // me falta que aparezca en una posicion random
            void ponerUneEnemigo(){
                Log.d("PonerEnemigo", "Voy a armar el sprite del enemigo");
                _Enemigo =Sprite.sprite("manitofacebook.png");

                Log.d("PonerEnemigo", "declaro la posicion incial");
                CCPoint posicionInicial;
                posicionInicial = new CCPoint();

                Log.d("PonerEnemigo", "determino la posicion x al azar");
                Random generadorDeAzar;
                generadorDeAzar = new Random();
                float anchoEnemigo;
                anchoEnemigo = _Enemigo.getWidth();
                posicionInicial.x = generadorDeAzar.nextInt((int)(_Pantalla.getWidth()-anchoEnemigo));
                posicionInicial.x += anchoEnemigo/2;


                Log.d("PonerEnemigo", "determino la posicion y al azar");
                Random generadorDeAzar2;
                generadorDeAzar2 = new Random();
                float altoEnemigo;
                altoEnemigo = _Enemigo.getHeight();
                posicionInicial.y = generadorDeAzar2.nextInt((int)(_Pantalla.getHeight()-altoEnemigo));
                posicionInicial.y += altoEnemigo/2;



                Log.d("PonerEnemigo", "ubico el sprite");
                _Enemigo.setPosition(posicionInicial.x,posicionInicial.y);

                Log.d("PonerEnemigo", "lo agrego a la capa");
                super.addChild(_Enemigo,10);

            }

            // cuando esta tocando al sprite hago un else if si hay interseccion y
            // llama al mover del otro sprite para que haga el cuadrado
            // y ovbiamente su respectiva funcion que haga que se muevan


            @Override
            public boolean ccTouchesBegan(MotionEvent event){
                float xTocada, yTocada;
                xTocada= event.getX();
                yTocada= _Pantalla.getHeight() - event.getY();
                Log.d("Control de toque", "Comienza toque: X:"+xTocada + " - Y: " + yTocada);
                if (InterseccionEntrePuntoySprite(_Jugador,xTocada,yTocada)){
                    moverjugador(xTocada,yTocada);
                    _EstaTocandoAlJugador = true;
                }
                else{
                    _EstaTocandoAlJugador = false;
                }

                if (InterseccionEntrePuntoySprite(_Enemigo,xTocada,yTocada)){
                    moverenemigo(xTocada,yTocada);
                    _EstaTocandoAlEnemigo = true;
                }
                else{
                    _EstaTocandoAlEnemigo = false;
                }

                return true;
            }

            @Override
            public boolean ccTouchesMoved(MotionEvent event){
                float xTocada, yTocada;
                xTocada= event.getX();
                yTocada= _Pantalla.getHeight() - event.getY();
                Log.d("Control de toque", "Mueve toque: X:"+xTocada + " - Y: " + yTocada);
                if (_EstaTocandoAlJugador){
                    moverjugador(xTocada,yTocada);
                }
                if (_EstaTocandoAlEnemigo){
                    moverenemigo(xTocada,yTocada);
                }
                return true;
            }

            @Override
            public boolean ccTouchesEnded(MotionEvent event){
                float xTocada, yTocada;
                xTocada= event.getX();
                yTocada= _Pantalla.getHeight() - event.getY();
                Log.d("Control de toque", "Mueve toque: X:"+xTocada + " - Y: " + yTocada);
                _EstaTocandoAlEnemigo = false;
                _EstaTocandoAlJugador = false;
                return true;
            }

            void moverjugador (float xAMover, float yAmover){
                Log.d("Mover Jugador", "Me piden que ubique en x:"+xAMover + " - Y: " + yAmover);
                _Jugador.setPosition(xAMover,yAmover);
            }

            void moverenemigo (float xAMover, float yAmover){
                Log.d("Mover Jugador", "Me piden que ubique en x:"+xAMover + " - Y: " + yAmover);
                _Enemigo.setPosition(xAMover,yAmover);
            }



            public boolean InterseccionEntrePuntoySprite(Sprite SpriteAVerificar, Float
                    puntoXAVerificar, Float puntoYAVerificar) {
                Boolean HayInterseccion=false;

                //Determino los bordes de cada Sprite
                Float SpArriba, SpAbajo, SpDerecha, SpIzquierda;
                SpArriba=SpriteAVerificar.getPositionY() + SpriteAVerificar.getHeight()/2;
                SpAbajo=SpriteAVerificar.getPositionY() - SpriteAVerificar.getHeight()/2;
                SpDerecha=SpriteAVerificar.getPositionX() + SpriteAVerificar.getWidth()/2;
                SpIzquierda=SpriteAVerificar.getPositionX() - SpriteAVerificar.getWidth()/2;

                Log.d("IntEntSpriteyPunto", "Sp Arr: " +SpArriba+" - Ab: " +SpAbajo + " - Der: " + SpDerecha +" - Izq: " + SpIzquierda);
                Log.d("IntEntSpriteyPunto", "X: " +puntoXAVerificar+" - Y: " +puntoYAVerificar );

                if(puntoXAVerificar >= SpIzquierda && puntoXAVerificar <= SpDerecha && puntoYAVerificar >= SpAbajo && puntoYAVerificar <= SpArriba) {
                    HayInterseccion = true;
                }

                Log.d("IntEntSpriteyPunto", "Hay Interseccion " +HayInterseccion);

                return HayInterseccion;
            }

            public boolean InterseccionEntreSprites(Sprite Sprite1, Sprite Sprite2) {
                Boolean HayInterseccion = false;
                //Determino los bordes de cada Sprite
                Float Sp1Arriba, Sp1Abajo, Sp1Derecha, Sp1Izquierda, Sp2Arriba,
                        Sp2Abajo, Sp2Derecha, Sp2Izquierda;

                Sp1Arriba = Sprite1.getPositionY() + Sprite1.getHeight() / 2;
                Sp1Abajo = Sprite1.getPositionY() - Sprite1.getHeight() / 2;
                Sp1Derecha = Sprite1.getPositionX() + Sprite1.getWidth() / 2;
                Sp1Izquierda = Sprite1.getPositionX() - Sprite1.getWidth() / 2;

                Sp2Arriba = Sprite2.getPositionY() + Sprite2.getHeight() / 2;
                Sp2Abajo = Sprite2.getPositionY() - Sprite2.getHeight() / 2;
                Sp2Derecha = Sprite2.getPositionX() + Sprite2.getWidth() / 2;
                Sp2Izquierda = Sprite2.getPositionX() - Sprite2.getWidth() / 2;


                Log.d("IntEntSprites", "Sp1 Arr: " +Sp1Arriba+" - Ab: " +Sp1Abajo + " - Der: " + Sp1Derecha +" - Izq: " + Sp1Izquierda);
                Log.d("IntEntSprites", "Sp2 Arr: " +Sp2Arriba+" - Ab: " +Sp2Abajo + " - Der: " + Sp2Derecha +" - Izq: " + Sp2Izquierda);

                //Me fijo si el vértice superior derecho de Sp1 está dentro de Sp2
                if(Sp1Arriba >= Sp2Abajo && Sp1Arriba<=Sp2Arriba && Sp1Derecha >= Sp2Izquierda && Sp1Derecha <= Sp2Derecha){
                    HayInterseccion=true;
                    Log.d("IntEntSprites", "interseccion caso 1");
                }

                //Me fijo si el vértice superior izquierdo de Sp1 está dentro de Sp2
                if(Sp1Arriba >= Sp2Abajo && Sp1Arriba<=Sp2Arriba && Sp1Derecha >= Sp2Izquierda && Sp1Izquierda <= Sp2Derecha){
                    HayInterseccion=true;
                    Log.d("IntEntSprites", "interseccion caso 2");
                }

                //Me fijo si el vértice inferior derecho de Sp1 está dentro de Sp2
                if(Sp1Abajo >= Sp2Abajo && Sp1Abajo<=Sp2Arriba && Sp1Derecha >= Sp2Izquierda && Sp1Derecha <= Sp2Derecha){
                    HayInterseccion=true;
                    Log.d("IntEntSprites", "interseccion caso 1");
                }

                //Me fijo si el vértice inferior izquierdo de Sp1 está dentro de Sp2
                if(Sp1Abajo >= Sp2Abajo && Sp1Abajo<=Sp2Arriba && Sp1Izquierda >= Sp2Izquierda && Sp1Izquierda <= Sp2Derecha){
                    HayInterseccion=true;
                    Log.d("IntEntSprites", "interseccion caso 1");
                }

                //  ---------------
                //Me fijo si el vértice superior derecho de Sp2 está dentro de Sp1
                if(Sp2Arriba >= Sp1Abajo && Sp2Arriba<=Sp1Arriba && Sp2Derecha >= Sp1Izquierda && Sp2Derecha <= Sp1Derecha){
                    HayInterseccion=true;
                    Log.d("IntEntSprites", "interseccion caso 5");
                }

                //Me fijo si el vértice superior izquierdo de Sp2 está dentro de Sp1
                if(Sp2Arriba >= Sp1Abajo && Sp2Arriba<=Sp1Arriba && Sp2Derecha >= Sp1Izquierda && Sp2Izquierda <= Sp1Derecha){
                    HayInterseccion=true;
                    Log.d("IntEntSprites", "interseccion caso 6");
                }

                //Me fijo si el vértice inferior derecho de Sp2 está dentro de Sp1
                if(Sp2Abajo >= Sp1Abajo && Sp2Abajo<=Sp1Arriba && Sp2Derecha >= Sp1Izquierda && Sp2Derecha <= Sp1Derecha){
                    HayInterseccion=true;
                    Log.d("IntEntSprites", "interseccion caso 7");
                }

                //Me fijo si el vértice inferior izquierdo de Sp2 está dentro de Sp1
                if(Sp2Abajo >= Sp1Abajo && Sp2Abajo<=Sp1Arriba && Sp2Izquierda >= Sp1Izquierda && Sp2Izquierda <= Sp1Derecha){
                    HayInterseccion=true;
                    Log.d("IntEntSprites", "interseccion caso 8");
                }

                Log.d("IntEntSprites", "hay interseccion: "+HayInterseccion);

                return HayInterseccion;
            }

            public void detectarColisiones (float deltaTiempo) {
                Log.d("DetectarColisiones", "Me fijo si algun enemigo choco al jugador");

                boolean huboColision;
                huboColision = false;

                if (InterseccionEntreSprites(_Jugador,_Enemigo)){
                    huboColision= true;
                }

                if (huboColision == true){
                    Log.d("DetectarColisiones", "Hubo una colision");

                }

                if (huboColision == true && _EstaTocandoAlJugador ){
                    moverenuncuadrado(_Enemigo);
                }

                if (huboColision == true && _EstaTocandoAlEnemigo ){
                    moverenuncuadrado(_Jugador);
                }

            }


            public void moverenuncuadrado(Sprite sprite){

                Log.d("moverencuadrado", "defino los segmentos de la trayectoria");
                MoveBy moverAbajo, moverDerecha,moverArriba,moverIzquierda;
                moverAbajo = MoveBy.action(1,0,-100);
                moverDerecha = MoveBy.action(1,100,0);
                moverArriba = MoveBy.action(1,0,100);
                moverIzquierda = MoveBy.action(1,-100,0);

                Log.d("moverencuadrado", "ejecuto la secuencia de movimientos");
                IntervalAction secuenciacuadrado;
                secuenciacuadrado = Sequence.actions(moverAbajo,moverDerecha,moverArriba,moverIzquierda);
                sprite.runAction(secuenciacuadrado);
            }


        }

        class capaGameOver extends Layer {

            public capaGameOver(){
                Log.d("CapaJuego", "Comienza el constructor");

                Log.d("CapaJuego", "Voy a ubicar el jugador en su poscicion inicial");
                ponerJugador();

                Log.d("CapaJuego", "Voy a ubicar la imagen de fondo");
                ponerImagenFondo();

                Log.d("CapaJuego", "Voy a ubicar el enemigo");
                ponerUneEnemigo();

                Log.d("CapaJuego", "inicio el verificador de colisiones");
                super.schedule("detectarColisiones", 0.25f);

                Log.d("CapaJuego", "habilito el touch");
                setIsTouchEnabled(true);
            }

            // me falta que aparezca en una posicion random
            void ponerJugador(){
                Log.d("PonerJugador", "Le asigno la imagen grafica al Sprite del jugador");
                _Jugador = Sprite.sprite("jugador.png");

                Log.d("PonerJugador", "declaro la posicion incial");
                CCPoint posicionInicial;
                posicionInicial = new CCPoint();

                Log.d("PonerJugador", "determino la posicion x al azar");
                Random generadorDeAzar;
                generadorDeAzar = new Random();
                float anchoJugador;
                anchoJugador = _Jugador.getWidth();
                posicionInicial.x = generadorDeAzar.nextInt((int)(_Pantalla.getWidth()-anchoJugador));
                posicionInicial.x += anchoJugador/2;


                Log.d("PonerJugador", "determino la posicion y al azar");
                Random generadorDeAzar2;
                generadorDeAzar2 = new Random();
                float altoJugador;
                altoJugador = _Jugador.getHeight();
                posicionInicial.y = generadorDeAzar2.nextInt((int)(_Pantalla.getHeight()-altoJugador));
                posicionInicial.y += altoJugador/2;

                Log.d("PonerEnemigo", "ubico el sprite");
                _Jugador.setPosition(posicionInicial.x,posicionInicial.y);

                Log.d("PonerJugador", "Lo agrego a la capa");
                super.addChild(_Jugador,10);
            }


            void ponerImagenFondo() {
                Sprite imagenFondo;
                Log.d("PonerFondo", "Le asigno la imagen grafica al Sprite del fondo");
                imagenFondo = Sprite.sprite("fondocuadrado.png");

                Log.d("PonerFondo", "Lo ubico");
                float factorAncho,factorAlto;
                factorAncho = _Pantalla.getWidth()/imagenFondo.getWidth();
                factorAlto = _Pantalla.getHeight()/imagenFondo.getHeight();
                imagenFondo.setPosition(_Pantalla.getWidth()/2,_Pantalla.getHeight()/2);

                Log.d("PonerFondo", "Le escalo para que ocupe toda la pantalla");
                imagenFondo.runAction(ScaleBy.action(0.01f,factorAncho,factorAlto));

                Log.d("PonerFondo", "Lo agrego a la capa");
                super.addChild(imagenFondo,-10);
            }


            // me falta que aparezca en una posicion random
            void ponerUneEnemigo() {
                Log.d("PonerEnemigo", "Voy a armar el sprite del enemigo");
                _Enemigo = Sprite.sprite("manitofacebook.png");

                Log.d("PonerEnemigo", "declaro la posicion incial");
                CCPoint posicionInicial;
                posicionInicial = new CCPoint();

                Log.d("PonerEnemigo", "determino la posicion x al azar");
                Random generadorDeAzar;
                generadorDeAzar = new Random();
                float anchoEnemigo;
                anchoEnemigo = _Enemigo.getWidth();
                posicionInicial.x = generadorDeAzar.nextInt((int) (_Pantalla.getWidth() - anchoEnemigo));
                posicionInicial.x += anchoEnemigo / 2;


                Log.d("PonerEnemigo", "determino la posicion y al azar");
                Random generadorDeAzar2;
                generadorDeAzar2 = new Random();
                float altoEnemigo;
                altoEnemigo = _Enemigo.getHeight();
                posicionInicial.y = generadorDeAzar2.nextInt((int) (_Pantalla.getHeight() - altoEnemigo));
                posicionInicial.y += altoEnemigo / 2;


                Log.d("PonerEnemigo", "ubico el sprite");
                _Enemigo.setPosition(posicionInicial.x, posicionInicial.y);

                Log.d("PonerEnemigo", "lo agrego a la capa");
                super.addChild(_Enemigo, 10);

            }


        }





    }
}
