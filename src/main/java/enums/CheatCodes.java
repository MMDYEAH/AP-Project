package enums;

public enum CheatCodes {
    REMOVE_CARD_FROM_ENEMY_HAND("q"),
    ADD_CARD_FORM_DECK_TO_MY_HAND("i"),
    DESTROY_SIEGE_FACTION_OF_ENEMY("x"),
    DESTROY_CLOSE_COMBAT_OF_ENEMY("s"),
    DESTROY_RANGED_COMBAT_OF_ENEMY("y"),
    ADD_CARD_FROM_HAND_TO_BORD("a"),
    REMOVE_ALL_CARDS_OF_CURRENT_USER("l");

    private String cheatCode;
    CheatCodes(String cheatCode){
        this.cheatCode = cheatCode;
    }
    public String getApplyOfCheatCode(String cheatCode){
        if(cheatCode.equals(this.cheatCode)) return cheatCode;
        else return null;
    }
}
