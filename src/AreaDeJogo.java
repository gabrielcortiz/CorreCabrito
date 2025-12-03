public class AreaDeJogo {
    private int[][] matrizAdjacencia;
    private int posicaoCabrito;
    private int posicaoCarcara;
    private Turno turnoAtual;
    private boolean superPuloJaUsado;
    private int totalDeJogadas;

    public AreaDeJogo() {
        matrizAdjacencia = new int[][] {
                {0, 1, 0, 1, 1, 0}, // Linha 0
                {1,0,1,0,0,1}, // Linha 1
                {0,1,0,1,0,0}, //linha2
                {1,0,1,0,1,0}, //linha3
                {1,0,0,1,0,1}, //linha4
                {0,1,0,0,1,0}, //linha5
        };
        posicaoCabrito = 1;
        posicaoCarcara = 2;
        turnoAtual = Turno.CABRITO;
        superPuloJaUsado = false;
        totalDeJogadas = 0;
    }

    public void tentarMover(int posicaoDeDestino) throws MovimentoInvalidoException, CapturaException {
        int posicaoAtual = (turnoAtual == Turno.CABRITO) ? posicaoCabrito : posicaoCarcara;

        // Se o jogador clicar onde já está, cancela tudo.
        if (posicaoDeDestino == posicaoAtual) {
            throw new MovimentoInvalidoException("Você já está nesta posição!");
        }

        // --- Verificações do Cabrito ---
        if (turnoAtual == Turno.CABRITO) {
            //  Tentou mover para cima do Carcará
            if (posicaoDeDestino == posicaoCarcara) {
                throw new CapturaException("O cabrito foi capturado!");
            }
        }

        if (matrizAdjacencia[posicaoAtual][posicaoDeDestino] != 1) {
            // NÃO É ADJACENTE.
            // O jogador clicou em um lugar errado.
            if (turnoAtual == Turno.CABRITO && !superPuloJaUsado ){
                if (matrizAdjacencia[posicaoAtual][posicaoDeDestino] == 0){
                    superPuloJaUsado = true;
                }
            }
            else {
                throw new MovimentoInvalidoException("Você não pode se mover para essa posição.");
            }
        }
        // movimento é VÁLIDO!
        //  aqui  realmente move a peça e troca o turno ...
        if (matrizAdjacencia[posicaoAtual][posicaoDeDestino] == 0){
            superPuloJaUsado = true;
        }

        if (turnoAtual == Turno.CABRITO){
            turnoAtual = Turno.CARCARA;
            posicaoCabrito = posicaoDeDestino;
        }else {
            if (posicaoDeDestino == posicaoCabrito){
                throw new CapturaException("Cabrito capturado !");
            }
            turnoAtual = Turno.CABRITO;
            posicaoCarcara = posicaoDeDestino;
        }
        totalDeJogadas += 1;
    }

    public int getPosicaoCabrito() {
        return this.posicaoCabrito;
    }

    public boolean getSuperPuloJaUsado(){
        return this.superPuloJaUsado;
    }

    public int getPosicaoCarcara() {
        return this.posicaoCarcara;
    }

    public Turno getTurnoAtual() {
        return this.turnoAtual;
    }
    public int getTotalDeJogadas(){
        return this.totalDeJogadas;
    }

    public int[][] getMatrizAdjacencia() {
        return this.matrizAdjacencia;
    }
}
