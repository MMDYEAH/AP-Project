package controller;

import model.*;
import view.PreGameMenu;

import java.util.ArrayList;

public class PreGameMenuController {
    PreGameMenu preGameMenu;

    public PreGameMenuController(PreGameMenu preGameMenu) {
        this.preGameMenu = preGameMenu;
    }

    public void initialize() {
        ArrayList<FactionLeaderCard> realmsNorthernFactionCards = new ArrayList<>();
        ArrayList<Card> realmsNorthernUnitCards = new ArrayList<>();
        addNorthernRealmsFactionCards(realmsNorthernFactionCards);
        addNorthernRealmsCard(realmsNorthernUnitCards);
        addNaturalCards(realmsNorthernUnitCards);
        RealmsNorthenFaction realmsNorthenFaction = new RealmsNorthenFaction(realmsNorthernFactionCards, realmsNorthernUnitCards);
        ArrayList<FactionLeaderCard> monstersFactionCards = new ArrayList<>();
        ArrayList<Card> monstersUnitCards = new ArrayList<>();
        addMonstersFactionCards(monstersFactionCards);
        addMonstersCard(monstersUnitCards);
        addNaturalCards(monstersUnitCards);
        MonstersFaction monstersFaction = new MonstersFaction(monstersFactionCards, monstersUnitCards);
        ArrayList<FactionLeaderCard> nilfgaardFactionCards = new ArrayList<>();
        ArrayList<Card> nilfgaardUnitCards = new ArrayList<>();
        addNilfgaardFactionCards(nilfgaardFactionCards);
        addNilfgaardCard(nilfgaardUnitCards);
        addNaturalCards(nilfgaardUnitCards);
        EmpireNilfgaardianFaction empireNilfgaardianFaction = new EmpireNilfgaardianFaction(nilfgaardFactionCards, nilfgaardUnitCards);
        ArrayList<FactionLeaderCard> skelligeFactionCards = new ArrayList<>();
        ArrayList<Card> skelligeUnitCards = new ArrayList<>();
        addSkelligeFactionCards(skelligeFactionCards);
        addSkelligeCard(skelligeUnitCards);
        addNaturalCards(skelligeUnitCards);
        SkelligeFaction skelligeFaction = new SkelligeFaction(skelligeFactionCards, skelligeUnitCards);
        ArrayList<FactionLeaderCard> scoiataelFactionCards = new ArrayList<>();
        ArrayList<Card> scoiataelUnitCards = new ArrayList<>();
        addScoiataelFactionCards(scoiataelFactionCards);
        addScoiataelCard(scoiataelUnitCards);
        addNaturalCards(scoiataelUnitCards);
        ScoiataelFaction scoiataelFaction = new ScoiataelFaction(scoiataelFactionCards, scoiataelUnitCards);
        App.setEmpireNilfgaardianFaction(empireNilfgaardianFaction);
        App.setMonstersFaction(monstersFaction);
        App.setScoiataelFaction(scoiataelFaction);
        App.setSkelligeFaction(skelligeFaction);
        App.setRealmsNorthenFaction(realmsNorthenFaction);
        //TODO: set unit of all cards
    }

    private void addScoiataelCard(ArrayList<Card> scoiataelUnitCards) {
        for (int i = 0; i < 2; i++) {
            scoiataelUnitCards.add(new Solider("Vrihedd Brigade Veteran <agile>", 5, false
                    , this.getClass().getResource("/pics/scoiatael/VriheddBrigadeVeteran" + (i + 1) + ".jpg").toExternalForm()));
        }
        for (int i = 0; i < 3; i++) {
            scoiataelUnitCards.add(new Muster("Elven Skirmisher <ranged>", 2, false
                    , this.getClass().getResource("/pics/scoiatael/ElvenSkirmisher" + (i + 1) + ".jpg").toExternalForm()));
            scoiataelUnitCards.add(new Solider("Dol Blathanna Scout <agile>", 6, false
                    , this.getClass().getResource("/pics/scoiatael/DolBlathannaScout" + (i + 1) + ".jpg").toExternalForm()));
            scoiataelUnitCards.add(new Muster("Dwarven Skirmisher <close>", 3, false
                    , this.getClass().getResource("/pics/scoiatael/DwarvenSkirmisher" + (i + 1) + ".jpg").toExternalForm()));
            scoiataelUnitCards.add(new Medic("Havekar Healer <ranged>", 0, false
                    , this.getClass().getResource("/pics/scoiatael/HavekarHealer" + (i + 1) + ".jpg").toExternalForm()));
            scoiataelUnitCards.add(new Muster("Havekar Smuggler <close>", 5, false
                    , this.getClass().getResource("/pics/scoiatael/HavekarSmuggler" + (i + 1) + ".jpg").toExternalForm()));
        }
        scoiataelUnitCards.add(new Solider("Yaevinn <agile>", 6, false
                , this.getClass().getResource("/pics/scoiatael/Yaevinn.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Ciaran aep <agile>", 3, false
                , this.getClass().getResource("/pics/scoiatael/CiaranAep.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Dennis Cranmer <close>", 6, false
                , this.getClass().getResource("/pics/scoiatael/DennisCranmer.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Dol Blathanna Archer <ranged>", 4, false
                , this.getClass().getResource("/pics/scoiatael/DolBlathannaArcher.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Ida Emean Aep <ranged>", 6, false
                , this.getClass().getResource("/pics/scoiatael/IdaEmeanAep.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Riordain <ranged>", 1, false
                , this.getClass().getResource("/pics/scoiatael/Riordain.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Toruviel <ranged>", 2, false
                , this.getClass().getResource("/pics/scoiatael/Toruviel.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Vrihedd Brigade Recruit <ranged>", 4, false
                , this.getClass().getResource("/pics/scoiatael/VriheddBrigadeRecruit.jpg").toExternalForm()));
        scoiataelUnitCards.add(new MoralBooster("Milva <ranged>", 10, false
                , this.getClass().getResource("/pics/scoiatael/Milva.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Seasenthessis <ranged>", 10, true
                , this.getClass().getResource("/pics/scoiatael/Seasenthessis.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Scorch("Schirru <siege>", 8, false
                , this.getClass().getResource("/pics/scoiatael/Schirru.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Eithne <ranged>", 10, true
                , this.getClass().getResource("/pics/scoiatael/Eithne.jpg").toExternalForm()));
        scoiataelUnitCards.add(new MoralBooster("Isengrim Faoiltiarna <close>", 10, true
                , this.getClass().getResource("/pics/scoiatael/IsengrimFaoiltiarna.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Iorveth <ranged>",10,true,
                this.getClass().getResource("pics/scoiatael/Iorveth.jpg").toExternalForm()));
        scoiataelUnitCards.add(new Solider("Filavandrel <agile>",6,false,
                this.getClass().getResource("pics/scoiatael/Filavandrel.jpg").toExternalForm()));
        for(int i=0;i<5;i++){
            scoiataelUnitCards.add(new Solider("Mahakaman Defender <close>",5,false,
                    this.getClass().getResource("pics/scoiatael/Mahakam"+i+1+".jpg").toExternalForm()));
        }
        scoiataelUnitCards.add(new Solider("Barclay Els <agile>",6,false,
                this.getClass().getResource("pics/scoiatael/Barclay.jpg").toExternalForm()));
    }

    private void addScoiataelFactionCards(ArrayList<FactionLeaderCard> factionLeaderCards) {
        factionLeaderCards.add(new FactionLeaderCard("Daisy Of The Valley",
                this.getClass().getResource("/pics/scoiatael/faction/DaisyOfTheValley.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("Hope Of The Aen Seidhe",
                this.getClass().getResource("/pics/scoiatael/faction/HopeOfTheAenSeidhe.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("Pureblood Elf",
                this.getClass().getResource("/pics/scoiatael/faction/PurebloodElf.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("Queen Of Dol Blathanna",
                this.getClass().getResource("/pics/scoiatael/faction/QueenOfDolBlathanna.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("The Beautiful",
                this.getClass().getResource("/pics/scoiatael/faction/TheBeautiful.jpg").toExternalForm()));
    }

    private void addSkelligeCard(ArrayList<Card> skelligeUnitCards) {
        for (int i = 0; i < 3; i++) {
            skelligeUnitCards.add(new Mardroeme("Mardroeme <spell>"
                    , this.getClass().getResource("/pics/skellige/Mardroeme.jpg").toExternalForm()));
            skelligeUnitCards.add(new TightBound("Clan Drummond Shieldmaiden <close>", 6, false
                    , this.getClass().getResource("/pics/skellige/ClanDrummondShieldmaiden" + (i + 1) + ".jpg").toExternalForm()));
            skelligeUnitCards.add(new Solider("Clan Brokvar Archer <ranged>", 6, false
                    , this.getClass().getResource("/pics/skellige/ClanBrokvarArcher.jpg").toExternalForm()));
            skelligeUnitCards.add(new TightBound("Clan An Craite <close>", 6, false
                    , this.getClass().getResource("/pics/skellige/ClanAnCraite.jpg").toExternalForm()));
            skelligeUnitCards.add(new TightBound("War Longship <siege>", 6, false
                    , this.getClass().getResource("/pics/skellige/WarLongship.jpg").toExternalForm()));
            skelligeUnitCards.add(new Muster("Light Longship <ranged>", 4, false
                    , this.getClass().getResource("/pics/skellige/LightLongship.jpg").toExternalForm()));
            skelligeUnitCards.add(new Berserker("Young Berserker <ranged>", 3, false
                    , this.getClass().getResource("/pics/skellige/YoungBerserker.jpg").toExternalForm()));
        }
        skelligeUnitCards.add(new Berserker("Berserker <close>", 1, false
                , this.getClass().getResource("/pics/skellige/Berserker.jpg").toExternalForm()));
        //TODO: transform berserker and young berserker in logic
        skelligeUnitCards.add(new Solider("Svanrige <close>", 4, false
                , this.getClass().getResource("/pics/skellige/Svanrige.jpg").toExternalForm()));
        skelligeUnitCards.add(new Solider("Udalryk <close>", 4, false
                , this.getClass().getResource("/pics/skellige/Udalryk.jpg").toExternalForm()));
        skelligeUnitCards.add(new Solider("Donar an Hindar <close>", 4, false
                , this.getClass().getResource("/pics/skellige/DonarAnHindar.jpg").toExternalForm()));
        skelligeUnitCards.add(new Solider("Madman Lugos <close>", 6, false
                , this.getClass().getResource("/pics/skellige/MadmanLugos.jpg").toExternalForm()));
        skelligeUnitCards.add(new Muster("Cerys <close>", 10, true
                , this.getClass().getResource("/pics/skellige/Cerys.jpg").toExternalForm()));
        skelligeUnitCards.add(new Transformers("Kambi <close>", 0, true
                , this.getClass().getResource("/pics/skellige/Kambi.jpg").toExternalForm()));
        skelligeUnitCards.add(new Medic("Birna Bran <close>", 2, false
                , this.getClass().getResource("/pics/skellige/BirnaBran.jpg").toExternalForm()));
        skelligeUnitCards.add(new Scorch("Clan Dimun Pirate <ranged>", 6, false
                , this.getClass().getResource("/pics/skellige/ClanDimunPirate.jpg").toExternalForm()));
        skelligeUnitCards.add(new UnitMardroeme("Ermion <ranged>", 8, true
                , this.getClass().getResource("/pics/skellige/Ermion.jpg").toExternalForm()));
        skelligeUnitCards.add(new Solider("Hjalmar <ranged>", 10, true
                , this.getClass().getResource("/pics/skellige/Hjalmar.jpg").toExternalForm()));
        skelligeUnitCards.add(new UnitCommandersHorn("Draig Bon-Dhu <siege>", 2, false
                , this.getClass().getResource("/pics/skellige/DraigBonDhu.jpg").toExternalForm()));
        skelligeUnitCards.add(new MoralBooster("Olaf <agile>", 12, false
                , this.getClass().getResource("/pics/skellige/Olaf.jpg").toExternalForm()));
        skelligeUnitCards.add(new Solider("Blueboy Lugos <close>",6,false,
                this.getClass().getResource("pics/skellige/Blueboy.jpg").toExternalForm()));
        skelligeUnitCards.add(new Solider("Holger Blackhand <siege>",4,false,
                this.getClass().getResource("pics/skellige/Holger.jpg").toExternalForm()));
    }

    private void addSkelligeFactionCards(ArrayList<FactionLeaderCard> factionLeaderCards) {
        factionLeaderCards.add(new FactionLeaderCard("Crach An Craite",
                this.getClass().getResource("/pics/skellige/faction/CrachAnCraite.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("King Bran",
                this.getClass().getResource("/pics/skellige/faction/KingBran.jpg").toExternalForm()));
    }

    private void addNilfgaardCard(ArrayList<Card> nilfgaardUnitCards) {
        //TODO:************************************************
        for (int i = 0; i < 4; i++) {
            nilfgaardUnitCards.add(new TightBound("Impera Brigade Guard <close>", 3, false,
                    this.getClass().getResource("pics/nilfgaard/ImperaBrigadeGuard.jpg").toExternalForm()));
        }
        nilfgaardUnitCards.add(new Spy("Stefan Skellen <close>", 9, false,
                this.getClass().getResource("pics/nilfgaard/StefanSkellen.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Spy("Shilard Fitz-Oesterlen <close>", 7, false,
                this.getClass().getResource("pics/nilfgaard/Shilard.jpg").toExternalForm()));
        for (int i = 0; i < 2; i++) {
            nilfgaardUnitCards.add(new TightBound("Young Emissary <clode>", 5, false,
                    this.getClass().getResource("pics/nilfgaard/YoungEmissary" + i + 1 + ".jpg").toExternalForm()));
            nilfgaardUnitCards.add(new Solider("Black Infantry Archer <ranged>", 10, false,
                    this.getClass().getResource("pics/nilfgaard/BlackInfantryArcher" + i + 1 + ".jpg").toExternalForm()));
            nilfgaardUnitCards.add(new Medic("Etolian Auxiliary Archers <ranged>", 1, false,
                    this.getClass().getResource("pics/nilfgaard/EtolianAuxiliaryArchers" + i + 1 + ".jpg").toExternalForm()));
        }
        nilfgaardUnitCards.add(new Solider("Cahir Mawr Dyffryn aep Ceallach <close>", 6, false,
                this.getClass().getResource("pics/nilfgaard/CahirMawrDyffrynAepCeallach.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Spy("Vattier de Rideaux <close>", 4, false,
                this.getClass().getResource("pics/nilfgaard/VattierDeRideaux.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Medic("Menno Coehorn <close>", 10, true,
                this.getClass().getResource("pics/nilfgaard/MennoCoehorn.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Puttkammer <ranged>", 3, false,
                this.getClass().getResource("pics/nilfgaard/Puttkammer.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Assire var Anahid <ranged>", 6, false,
                this.getClass().getResource("pics/nilfgaard/Assire.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Tibor Eggebracht <ranged>", 10, true,
                this.getClass().getResource("pics/nilfgaard/TiborEggebracht.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Renuald aep Matsen <ranged>", 5, false,
                this.getClass().getResource("pics/nilfgaard/RenualdAepMatsen.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Fringilla Vigo <ranged>", 6, false,
                this.getClass().getResource("pics/nilfgaard/FringillaVigo.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Rotten Mangonel <siege>", 3, false,
                this.getClass().getResource("pics/nilfgaard/RottenMangonel.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Heavy Zerrikanian Fire Scorpion <siege>", 10, false,
                this.getClass().getResource("pics/nilfgaard/HeavyZerri.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Zerrikanian Fire Scorpion <siege>", 5, false,
                this.getClass().getResource("pics/nilfgaard/ZerrikanianFireScorpion.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Siege Engineer <siege>", 6, false,
                this.getClass().getResource("pics/nilfgaard/SiegeEngineer.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Morvran Voorhis <siege>", 10, true,
                this.getClass().getResource("pics/nilfgaard/MorvranVoorhis.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Albrich <ranged>", 2, false,
                this.getClass().getResource("pics/nilfgaard/Albrich.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider(" Cynthia <ranged>", 4, false,
                this.getClass().getResource("pics/nilfgaard/Cynthia.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Letho of Gulet <close>", 10, true,
                this.getClass().getResource("pics/nilfgaard/Letho.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Morteisen <close>", 3, false,
                this.getClass().getResource("pics/nilfgaard/Morteisen.jpg").toExternalForm()));
        for (int i = 0; i < 3; i++) {
            nilfgaardUnitCards.add(new TightBound("Nausicaa Cavalry Rider <close>", 2, false,
                    this.getClass().getResource("pics/nilfgaard/NausicaaCavalryRider.jpg").toExternalForm()));
        }
        nilfgaardUnitCards.add(new Solider("Rainfarn <close>", 4, false,
                this.getClass().getResource("pics/nilfgaard/Rainfarn.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Medic("Siege Technician <siege>",0,false,
                this.getClass().getResource("pics/nilfgaard/SiegeTechnician.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Sweers <ranged>",2,false,
                this.getClass().getResource("pics/nilfgaard/Sweers.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Vanhemar <ranged>",4,false,
                this.getClass().getResource("pics/nilfgaard/Vanhemar.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Spy("Vattier de Rideaux <close>",4,false,
                this.getClass().getResource("pics/nilfgaard/VattierDeRideaux.jpg").toExternalForm()));
        nilfgaardUnitCards.add(new Solider("Vreemde <close>",2,false,
                this.getClass().getResource("pics/nilfgaard/Vreemde.jpg").toExternalForm()));


    }


    private void addNilfgaardFactionCards(ArrayList<FactionLeaderCard> factionLeaderCards) {
        factionLeaderCards.add(new FactionLeaderCard("Emperor Of Nilfgaard",
                this.getClass().getResource("/pics/nilfgaard/faction/EmperorOfNilfgaard.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("His Impreial Majesty",
                this.getClass().getResource("/pics/nilfgaard/faction/HisImpreialMajesty.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("Invader Of The North",
                this.getClass().getResource("/pics/nilfgaard/faction/InvaderOfTheNorth.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("The Relentless",
                this.getClass().getResource("/pics/nilfgaard/faction/TheRelentless.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("The White Flame",
                this.getClass().getResource("/pics/nilfgaard/faction/TheWhiteFlame.jpg").toExternalForm()));
    }

    private void addMonstersCard(ArrayList<Card> monstersUnitCards) {
        for (int i = 0; i < 3; i++) {
            monstersUnitCards.add(new Muster("Arachas <close>", 4, false
                    , this.getClass().getResource("/pics/monsters/Arachas" + (i + 1) + ".jpg").toExternalForm()));
            monstersUnitCards.add(new Muster("Nekker <close>", 2, false
                    , this.getClass().getResource("/pics/monsters/Nekker" + (i + 1) + ".jpg").toExternalForm()));
            monstersUnitCards.add(new Muster("Ghoul <close>", 1, false
                    , this.getClass().getResource("/pics/monsters/Ghoul" + (i + 1) + ".jpg").toExternalForm()));
        }
        monstersUnitCards.add(new Solider("Draug <close>", 10, true
                , this.getClass().getResource("/pics/monsters/Draug.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Leshen <close>", 10, true
                , this.getClass().getResource("/pics/monsters/Leshen.jpg").toExternalForm()));
        monstersUnitCards.add(new MoralBooster("Kayran <agile>", 8, true
                , this.getClass().getResource("/pics/monsters/Kayran.jpg").toExternalForm()));
        monstersUnitCards.add(new Scorch("Toad <ranged>", 7, false
                , this.getClass().getResource("/pics/monsters/Toad.jpg").toExternalForm()));
        monstersUnitCards.add(new Muster("Arachas Behemoth <siege>", 6, false
                , this.getClass().getResource("/pics/monsters/ArachasBehemoth.jpg").toExternalForm()));
        monstersUnitCards.add(new Muster("Crone Weavess <close>", 6, false
                , this.getClass().getResource("/pics/monsters/CroneWeavess.jpg").toExternalForm()));
        monstersUnitCards.add(new Muster("Crone Whispess <close>", 6, false
                , this.getClass().getResource("/pics/monsters/CroneWhispess.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Earth Elemental <siege>", 6, false
                , this.getClass().getResource("/pics/monsters/EarthElemental.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Fiend <close>", 6, false
                , this.getClass().getResource("/pics/monsters/Fiend.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Fire Elemental <siege>", 6, false
                , this.getClass().getResource("/pics/monsters/FireElemental.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Forktail <close>", 5, false
                , this.getClass().getResource("/pics/monsters/Forktail.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Grave Hag <ranged>", 5, false
                , this.getClass().getResource("/pics/monsters/GraveHag.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Griffin <close>", 5, false
                , this.getClass().getResource("/pics/monsters/Griffin.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Ice Giant <siege>", 5, false
                , this.getClass().getResource("/pics/monsters/IceGiant.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Plague Maiden <close>", 5, false
                , this.getClass().getResource("/pics/monsters/PlagueMaiden.jpg").toExternalForm()));
        monstersUnitCards.add(new Muster("Vampire Katakan <close>", 5, false
                , this.getClass().getResource("/pics/monsters/VampireKatakan.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Werewolf <close>", 5, false
                , this.getClass().getResource("/pics/monsters/Werewolf.jpg").toExternalForm()));
        monstersUnitCards.add(new Muster("Vampire Bruxa <close>", 4, false
                , this.getClass().getResource("/pics/monsters/VampireBruxa.jpg").toExternalForm()));
        monstersUnitCards.add(new Muster("Vampire Ekimmara <close>", 4, false
                , this.getClass().getResource("/pics/monsters/VampireEkimmara.jpg").toExternalForm()));
        monstersUnitCards.add(new Muster("Vampire Fleder <close>", 4, false
                , this.getClass().getResource("/pics/monsters/VampireFleder.jpg").toExternalForm()));
        monstersUnitCards.add(new Muster("Vampire Garkain <close>", 4, false
                , this.getClass().getResource("/pics/monsters/VampireGarkain.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Cockatrice <ranged>", 2, false
                , this.getClass().getResource("/pics/monsters/Cockatrice.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Endrega <ranged>", 2, false
                , this.getClass().getResource("/pics/monsters/Endrega.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Foglet <close>", 2, false
                , this.getClass().getResource("/pics/monsters/Foglet.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Harpy <agile>", 2, false
                , this.getClass().getResource("/pics/monsters/Harpy.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Wyvern <ranged>", 2, false
                , this.getClass().getResource("/pics/monsters/Wyvern.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Imlerith <close>",10,true,
                this.getClass().getResource("pics/monsters/Imlerith.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Frightener <close>",5,false,
                this.getClass().getResource("pics/monsters/Frightener.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Celaeno Harpy <agile>",2,false,
                this.getClass().getResource("pics/monsters/CelaenoHarpy.jpg").toExternalForm()));
        monstersUnitCards.add(new Solider("Gargoyle <ranged>",2,false,
                this.getClass().getResource("pics/monsters/Gargoyle.jpg").toExternalForm()));
    }

    private void addMonstersFactionCards(ArrayList<FactionLeaderCard> factionLeaderCards) {
        factionLeaderCards.add(new FactionLeaderCard("Bringer of Death",
                this.getClass().getResource("/pics/monsters/faction/BringerOfDeath.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("King Of The Wild Hunt",
                this.getClass().getResource("/pics/monsters/faction/KingOfTheWildHunt.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("Commander Of Red Riders",
                this.getClass().getResource("/pics/monsters/faction/CommanderOfRedRiders.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("Destroyer Of Worlds",
                this.getClass().getResource("/pics/monsters/faction/DestroyerOfWorlds.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("The Treacherous",
                this.getClass().getResource("/pics/monsters/faction/TheTreacherous.jpg").toExternalForm()));
    }

    private void addNorthernRealmsFactionCards(ArrayList<FactionLeaderCard> factionLeaderCards) {
        factionLeaderCards.add(new FactionLeaderCard("The Siegemaster",
                this.getClass().getResource("/pics/northenRealms/faction/TheSiegemaster.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("The Steel-Forged",
                this.getClass().getResource("/pics/northenRealms/faction/TheSteelForged.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("King of Temeria",
                this.getClass().getResource("/pics/northenRealms/faction/KingOfTemeria.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("Lord Commander of the North",
                this.getClass().getResource("/pics/northenRealms/faction/LordCommanderOfTheNorth.jpg").toExternalForm()));
        factionLeaderCards.add(new FactionLeaderCard("Son of Medell",
                this.getClass().getResource("/pics/northenRealms/faction/SonOfMedell.jpg").toExternalForm()));
    }

    private void addNorthernRealmsCard(ArrayList<Card> realmsNorthernUnitCards) {
        for (int i = 0; i < 2; i++) {
            realmsNorthernUnitCards.add(new Solider("Ballista <siege>", 6, false
                    , this.getClass().getResource("/pics/northenRealms/Ballista.jpg").toExternalForm()));
            realmsNorthernUnitCards.add(new Solider("Redanian Foot Soldier <close>", 1, false
                    , this.getClass().getResource("/pics/northenRealms/RedanianFootSoldier" + (i + 1) + ".jpg").toExternalForm()));
            realmsNorthernUnitCards.add(new TightBound("Catapult <siege>", 8, false
                    , this.getClass().getResource("/pics/northenRealms/Catapult.jpg").toExternalForm()));
            realmsNorthernUnitCards.add(new Solider("Trebuchet <siege>",6,false,
                    this.getClass().getResource("pics/northenRealms/Trebuchet"+i+1+".jpg").toExternalForm()));
        }
        for (int i = 0; i < 3; i++) {
            realmsNorthernUnitCards.add(new MoralBooster("Kaedweni Siege Expert <siege>", 1, false
                    , this.getClass().getResource("/pics/northenRealms/KaedweniSiegeExpert" + (i + 1) + ".jpg").toExternalForm()));
            realmsNorthernUnitCards.add(new TightBound("Dragon Hunter <ranged>", 5, false
                    , this.getClass().getResource("/pics/northenRealms/DragonHunter.jpg").toExternalForm()));
        }
        for (int i = 0; i < 4; i++) {
            realmsNorthernUnitCards.add(new TightBound("Poor Fucking Infantry <close>", 1, false
                    , this.getClass().getResource("/pics/northenRealms/PoorFuckingInfantry.jpg").toExternalForm()));
        }
        realmsNorthernUnitCards.add(new Medic("Dun Banner Medic <siege>", 5, false
                , this.getClass().getResource("/pics/northenRealms/DunBannerMedic.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Esterad Thyssen <close>", 10, true
                , this.getClass().getResource("/pics/northenRealms/EsteradThyssen.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("John Natalis <close>", 10, true
                , this.getClass().getResource("/pics/northenRealms/JohnNatalis.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Philippa Eilhart <ranged>", 10, true
                , this.getClass().getResource("/pics/northenRealms/PhilippaEilhart.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Spy("Prince Stennis <close>", 5, false
                , this.getClass().getResource("/pics/northenRealms/PrinceStennis.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Sabrina Glevissing <ranged>", 4, false
                , this.getClass().getResource("/pics/northenRealms/SabrinaGlevissing.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Siege Tower <siege>", 6, false
                , this.getClass().getResource("/pics/northenRealms/SiegeTower.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Siegfried of Denesle <close>", 5, false
                , this.getClass().getResource("/pics/northenRealms/SiegfriedOfDenesle.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Spy("Sigismund Dijkstra <close>", 4, false
                , this.getClass().getResource("/pics/northenRealms/SigismundDijkstra.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Sile de Tansarville <ranged>", 5, false
                , this.getClass().getResource("/pics/northenRealms/SileDeTansarville.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Spy("Thaler <siege>", 1, false
                , this.getClass().getResource("/pics/northenRealms/Thaler.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Vernon Roche <close>", 10, true
                , this.getClass().getResource("/pics/northenRealms/VernonRoche.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Ves <close>", 5, false
                , this.getClass().getResource("/pics/northenRealms/Ves.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Yarpen Zirgrin <close>", 2, false
                , this.getClass().getResource("/pics/northenRealms/YarpenZirgrin.jpg").toExternalForm()));
        for(int i=0;i<3;i++) {
            realmsNorthernUnitCards.add(new TightBound("Blue Stripes Commando <close>", 4, false,
                    this.getClass().getResource("pics/northenRealms/BlueStripes.jpg").toExternalForm()));
        }
        realmsNorthernUnitCards.add(new Solider("Dethmold <ranged>",6,false,
                this.getClass().getResource("pics/northenRealms/Dethmold.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Keira Metz <ranged>",5,false,
                this.getClass().getResource("pics/northenRealms/Keira.jpg").toExternalForm()));
        realmsNorthernUnitCards.add(new Solider("Sheldon Skaggs <ranged>",4,false,
                this.getClass().getResource("pics/northenRealms/Sheldon.jpg").toExternalForm()));

    }

    private void addNaturalCards(ArrayList<Card> UnitCards) {
        for (int i = 0; i < 3; i++) {
            UnitCards.add(new BitingFrost("BitingFrost <weather>"
                    , this.getClass().getResource("/pics/neutral/BitingFrost.jpg").toExternalForm()));
            UnitCards.add(new ImpenetrableFog("ImpenetrableFog <weather>"
                    , this.getClass().getResource("/pics/neutral/ImpenetrableFog.jpg").toExternalForm()));
            UnitCards.add(new TorrentialRain("TorrentialRain <weather>"
                    , this.getClass().getResource("/pics/neutral/TorrentialRain.jpg").toExternalForm()));
            UnitCards.add(new Decoy("Decoy <spell>"
                    , this.getClass().getResource("/pics/neutral/Decoy.jpg").toExternalForm()));
            UnitCards.add(new Muster("Gaunter O’DImm Darkness <ranged>", 4, false
                    , this.getClass().getResource("/pics/neutral/GaunterO’DImmDarkness.jpg").toExternalForm()));
            UnitCards.add(new SkelligeStorm("Skellige Storm <weather>",
                    this.getClass().getResource("pics/neutral/Storm.jpg").toExternalForm()));
            UnitCards.add(new ClearWeather("Clear Weather <weather>",
                    this.getClass().getResource("pics/neutral/Clear.jpg").toExternalForm()));
            UnitCards.add(new SpellScorch("Scorch <spell>",
                    this.getClass().getResource("pics/neutral/Scorch.jpg").toExternalForm()));
        }
        UnitCards.add(new UnitCommandersHorn("Dandelion <close>", 2, false
                , this.getClass().getResource("/pics/neutral/Dandelion.jpg").toExternalForm()));
        UnitCards.add(new Solider("Emiel Regis <close>", 2, false
                , this.getClass().getResource("/pics/neutral/EmielRegis.jpg").toExternalForm()));
        UnitCards.add(new Muster("Gaunter O’Dimm <siege>", 2, false
                , this.getClass().getResource("/pics/neutral/GaunterO’Dimm.jpg").toExternalForm()));
        UnitCards.add(new Spy("Mysterious Elf <close>", 0, true
                , this.getClass().getResource("/pics/neutral/MysteriousElf.jpg").toExternalForm()));
        UnitCards.add(new MoralBooster("Olgierd Von Everc <agile>", 6, false
                , this.getClass().getResource("/pics/neutral/OlgierdVonEverc.jpg").toExternalForm()));
        UnitCards.add(new Solider("Triss Merigold <close>", 7, true
                , this.getClass().getResource("/pics/neutral/TrissMerigold.jpg").toExternalForm()));
        UnitCards.add(new Scorch("Villentretenmerth <close>", 7, false
                , this.getClass().getResource("/pics/neutral/Villentretenmerth.jpg").toExternalForm()));
        UnitCards.add(new Medic("Yennefer of Vengerberg <ranged>", 7, true
                , this.getClass().getResource("/pics/neutral/YenneferOfVengerberg.jpg").toExternalForm()));
        UnitCards.add(new Solider("Zoltan Chivay <close>", 5, false
                , this.getClass().getResource("/pics/neutral/ZoltanChivay.jpg").toExternalForm()));
        UnitCards.add(new Transformers("Cow <ranged>",0,false,
                this.getClass().getResource("pics/neutral/Cow.jpg").toExternalForm()));
        UnitCards.add(new Solider("Geralt of Rivia <close>",15,true,
                this.getClass().getResource("pics/neutral/Geralt.jpg").toExternalForm()));
        UnitCards.add(new Solider("Vesemir <close>",6,false,
                this.getClass().getResource("pics/neutral/Vesemir.jpg").toExternalForm()));
    }

    public void showCards() {
    }

    public void showFactions() {
    }

    public void showLeaders() {
    }

    public void showDeck() {
    }

    public void changeTurn() {
    }

    public void showCurrentUserInfo() {
    }

    public Result startGame() {
        return null;//TODO: delete this code and write
    }

    public Result selectFaction(String factionName) {
        return null;//TODO: delete this code and write
    }

    public Result createGame(String enemyUsername) {
        return null;//TODO: delete this code and write
    }

    public Result selectLeader(String leaderName) {
        return null;//TODO: delete this code and write
    }

    public Result saveDeckWithAddress(String address) {
        return null;//TODO: delete this code and write
    }

    public Result saveDeckWithName(String name) {
        return null;//TODO: delete this code and write
    }

    public Result loadDeckWithAddress(String address) {
        return null;//TODO: delete this code and write
    }

    public Result loadDeckWithName(String name) {
        return null;//TODO: delete this code and write
    }

    public Result addCardToDeck(String cardName, int count) {
        return null;//TODO: delete this code and write
    }

    public Result removeFromDeck(String cardName, int count) {
        return null;//TODO: delete this code and write
    }

    private void makeRandomFaction() {
    }
}
