package com.example.luiseduardo.infrafacil;

public class PlayersListView {

        private String id;
        private String idjogo;
        private String nome;
        private String rebuy;
        private String addon;
        private String valor;
        private int iconeRid;



    public PlayersListView(String status, int trabalho100)
        {
        }

        public PlayersListView(String id, String idjogo, String nome,String rebuy, String addon, String valor,   int iconeRid)

        {
            this.id = id;
            this.idjogo = idjogo;
            this.nome = nome;
            this.rebuy = rebuy;
            this.addon = addon;
            this.valor = valor;
            this.iconeRid = iconeRid;
        }
        public String getValor() { return valor; }

        public void setValor(String valor) { this.valor = valor; }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdjogo() {
            return idjogo;
        }

        public void setIdjogo(String idjogo) {
            this.idjogo = idjogo;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getRebuy() {
            return rebuy;
        }

        public void setRebuy(String rebuy) {
            this.rebuy = rebuy;
        }

        public String getAddon() {
            return addon;
        }

        public void setAddon(String addon) {
            this.addon = addon;
        }

        public int getIconeRid() {
            return iconeRid;
        }

        public void setIconeRid(int iconeRid) {
            this.iconeRid = iconeRid;
        }
}
