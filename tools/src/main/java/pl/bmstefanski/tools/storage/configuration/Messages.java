/*
 MIT License

 Copyright (c) 2018 Whippy Tools

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package pl.bmstefanski.tools.storage.configuration;

import org.diorite.config.Config;
import org.diorite.config.annotations.CustomKey;

import java.util.Arrays;
import java.util.List;

public interface Messages extends Config {

    @CustomKey("boolean-on") default String getBooleanOn() {
        return "&awlaczone";
    }

    @CustomKey("boolean-off") default String getBooleanOff() {
        return "&cwylaczone";
    }

    @CustomKey("no-permissions") default String getNoPermissions() {
        return "&cNie masz uprawnien do wykonania tej komendy! &7(%permission%)";
    }

    @CustomKey("only-player") default String getOnlyPlayer() {
        return "&cTa komende moze wykonac tylko gracz!";
    }

    @CustomKey("unknown-command") default String getUnknownCommand() {
        return "&cNie znaleziono takiej komendy! &7(%command%)";
    }

    @CustomKey("list-full") default String getListFull() {
        return "&7Aktualnie na serwerze: &e%online%";
    }

    @CustomKey("list-basic") default String getListBasic() {
        return "&7Aktualnie na serwerze jest &e%online%&7/&e%max% &7graczy!";
    }

    @CustomKey("player-not-found") default String getPlayerNotFound() {
        return "&cNie znaleziono gracza o nicku &7%player%&c!";
    }

    @CustomKey("gamemode-success") default String getGamemodeSuccess() {
        return "&7Zmieniono tryb gry na &e%gamemode%&7!";
    }

    @CustomKey("gamemode-success-other") default String getGamemodeSuccessOther() {
        return "&7Zmieniono tryb gry na &e%gamemode% &7dla &e%player%&7!";
    }

    @CustomKey("successfully-reloaded") default String getSuccessfullyReloaded() {
        return "&aPlugin zostal ponowanie zaladowany w &7%time% &asekund!";
    }

    @CustomKey("successfully-disabled") default String getSuccessfullyDisabled() {
        return "&cPomyslnie wylaczono plugin!";
    }

    default String getHealed() {
        return "&7Zostales uleczony!";
    }

    @CustomKey("healed-other") default String getHealedOther() {
        return "&7Uleczono gracza o nicku &e%player%&7!";
    }

    default String getFed() {
        return "&7Zostales nakarmiony!";
    }

    @CustomKey("fed-other") default String getFedOther() {
        return "&7Nakarmiono gracza o nicku &e%player%&7!";
    }

    @CustomKey("fly-switched") default String getFlySwitched() {
        return "&7Latanie zostalo %state%&7!";
    }

    @CustomKey("fly-switched-other") default String getFlySwitchedOther() {
        return "&7Latanie zostalo %state% &7dla gracza &e%player%&7!";
    }

    @CustomKey("setspawn-success") default String getSetspawnSuccess() {
        return "&7Pomyslnie utworzono spawn na koordynatach: &e%x%&7, &e%y%&7, &e%z%&7, &e%world%&7!";
    }

    @CustomKey("spawn-failed") default String getSpawnFailed() {
        return "&cSpawn nie jest utworzony! Uzyj komendy &7/setspawn&c, aby utworzyc";
    }

    @CustomKey("teleport-cancelled") default String getTeleportCancelled() {
        return "&cTeleportacja zostala przerwana!";
    }

    @CustomKey("currently-teleporting") default String getCurrentlyTeleporting() {
        return "&cJuz sie teleportujesz!";
    }

    @CustomKey("god-switched") default String getGodSwitched() {
        return "&7God zostal %state%&7!";
    }

    @CustomKey("god-switched-other") default String getGodSwitchedOther() {
        return "&7God zostal %state% &7dla &e%player%&7!";
    }

    @CustomKey("teleport-success") default String getTeleportSuccess() {
        return "&7Pomyslnie teleportowano!";
    }

    @CustomKey("broadcast-format") default String getBroadcastFormat() {
        return "&8[&3OGLOSZENIE&8] &7%message%";
    }

    default String getClear() {
        return "&7Wyczyszczono twoj ekwipunek!";
    }

    @CustomKey("clear-other") default String getClearOther() {
        return "&7Wyczyszczono ekwipunek gracza &e%player%&7!";
    }

    default List<String> getWhois() {
        return Arrays.asList(
                "&8&m-----------------------------------------&r",
                "  &7Nickname: &e%nickname%",
                "  &7UUID: &e%uuid%",
                "  &7IP: &e%ip%",
                "  &7Zarejestrowany: &e%registered%",
                "  &7Ostatnio widziany: &e%last%",
                "  &7Lokacja: &e%location%",
                "  &7HP: &e%hp%",
                "  &7Glod: &e%hunger%",
                "  &7Tryb gry: &e%gamemode%",
                "  &7God: %god%",
                "  &7Fly: %fly%",
                "&8&m-----------------------------------------");
    }

    @CustomKey("gamemode-fail") default String getGamemodeFail() {
        return "&cMusisz wybrac tryb!";
    }

    @CustomKey("ban-format") default List<String> getBanFormat() {
        return Arrays.asList(
                "&8&m---------------------------------------------------&r",
                "  &cZostales zbanowany przez: &e%punisher% &cdo &e%until%",
                "  &7Odbanuj sie na: &ewww.dzienkiStary.pl",
                "  &7Powod: %reason%",
                "&8&m---------------------------------------------------"
        );
    }

    @CustomKey("already-banned") default String getAlreadyBanned() {
        return "&cGracz &7%player% &cjest juz zbanowany!";
    }

    @CustomKey("default-reason") default String getDefaultReason() {
        return "&cAdmin ma zawsze racje!";
    }

    @CustomKey("permanent-ban") default String getPermanentBan() {
        return "&cna zawsze";
    }

    @CustomKey("not-banned") default String getNotBanned() {
        return "&cGracz &7%player% &cnie jest zbanowany.";
    }

    @CustomKey("successfully-unbanned") default String getSuccessfullyUnbanned() {
        return "&cPomyslnie odbanowano gracza &7%player%";
    }

    @CustomKey("successfully-banned") default String getSuccessfullyBanned() {
        return "&cPomyslnie zbanowano gracza &7%player%";
    }

    @CustomKey("cannot-ban-yourself") default String getCannotBanYourself() {
        return "&cNie mozesz sie zbanowac!";
    }

    default String getTeleport() {
        return "&7Teleportuje...";
    }

    @CustomKey("no-longer-afk") default String getNoLongerAfk() { return "&5Nie jestes juz dluzej AFK"; }

    @CustomKey("no-longer-afk-global") default String getNoLongerAfkGlobal() { return "&5Gracz &7%player% &5nie jest juz dluzej AFK"; }

    default String getAfk() { return "&5Jestes AFK"; }

    @CustomKey("afk-global") default String getAfkGlobal() { return "&5Gracz &7%player% &5jest teraz AFK"; }
 
    @CustomKey("hat-cant-be-air") default String getHatCantBeAir() { return "&cNie masz nic w rece!"; }

    default String getHat() { return "&6Ciesz sie nowa czapka!"; }

    @CustomKey("skull-only") default String getSkullOnly() { return "&6Dostales swoja glowe!"; }

    @CustomKey("skull-someone") default String getSkullSomeone() { return "&6Dostales glowe gracza &7%player%"; }

    @CustomKey("tp-success") default String getTpSuccess() {
        return "&7Pomyslnie przeteleportowano gracza &e%player% &7do gracza &e%target%";
    }

    @CustomKey("tp-failed") default String getTpFailed() {
        return "&cNie mozna teleportowac, poniewaz &7%player% &club &7%target% &cjest offline";
    }

    default String getTppos() {
        return "&7Teleportowano gracza &e%player% &7na koordynaty: x: &e%x%&7, y: &e%y%&7, z: &e%z%";
    }

    default String getDay() {
        return "&7Ustawiono dzien na swiecie &e%world%";
    }

    @CustomKey("world-not-found") default String getWorldNotFound() {
        return "&cNie znaleziono swiata o nazwie &7%world%";
    }

    default String getNight() {
        return "&7Ustawiono noc na swiecie &e%world%";
    }

    @CustomKey("cannot-repair") default String getCannotRepair() { return "&cNie mozesz naprawic niczego!"; }

    @CustomKey("cannot-repair-full") default String getCannotRepairFull() { return "&cNie mozesz naprawic "; }

    default String getRepaired() { return "&aNaprawiles &7%item%"; }

    @CustomKey("cannot-kick-yourself") default String getCannotKickYourself() {
        return "&cNie mozesz wyrzucic samego siebie!";
    }

    @CustomKey("too-long-nickname") default String getTooLongNickname() {
        return "&cTwoj nick jest za dlugi! Maksymalna dlugosc nicku to: &7%max%";
    }

    default String getStruck() {
        return "&7Zostales uderzony piorunem!";
    }

    @CustomKey("struck-other") default String getStruckOther() {
        return "&7Uderzyles piorunem gracza o nicku &e%player%.";
    }

    @CustomKey("set-nickname") default String getSetNickname() {
        return "&7Twoj nickname zostal zmieniony na %nickname%";
    }

    @CustomKey("set-nickname-other") default String getSetNicknameOther() {
        return "&7Zmieniles nickname gracza &e%player%&7, na %nickname%";
    }

    default String getRealname() {
        return "&7Twoj prawdziwy nickname: %nickname%";
    }

    @CustomKey("realname-other") default String getRealnameOther() {
        return "&7Prawdziwy nickname gracza &e%player%&7 to %nickname%";
    }

    default String getMarked() {
        return "&7Status oznaczonego zostal %state%";
    }

    @CustomKey("marked-other") default String getMarkedOther() {
        return "&7Zmieniles status oznaczonego dla &e%player%&7 na &e%state%";
    }

}
