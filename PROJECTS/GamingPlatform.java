// Problem 16 : Online Gaming Platform Ranking System

// --- Core Implementation ---

interface Rankable {
    void updateRank();
}

abstract class User {
    private String userId;
    private String username;
    private String email;

    public User(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public User(String userId, String username) {
        this(userId, username, "Unknown");
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public abstract void displayProfile();
}

abstract class GamePlayer extends User {
    private int gamesPlayed;
    private int wins;
    public static int totalUsers = 0;

    public GamePlayer(String userId, String username, String email, int gamesPlayed, int wins) {
        super(userId, username, email);
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        totalUsers++;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public double getWinRate() {
        if (gamesPlayed == 0) return 0.0;
        return ((double) wins / gamesPlayed) * 100;
    }

    public abstract int calculateXP(boolean isWin);
    public abstract int calculateXP(boolean isWin, int kills);
    public abstract void displayProfile();
}

class RankedPlayer extends GamePlayer implements Rankable {
    private String rankTier;
    private int rankPoints;
    private int seasonWins;

    public RankedPlayer(String userId, String username, String email, int gamesPlayed, int wins, String rankTier, int rankPoints) {
        super(userId, username, email, gamesPlayed, wins);
        this.rankTier = rankTier;
        this.rankPoints = rankPoints;
        this.seasonWins = 0;
    }

    public String getRankTier() {
        return rankTier;
    }

    public void setRankTier(String rankTier) {
        this.rankTier = rankTier;
    }

    public int getRankPoints() {
        return rankPoints;
    }

    public void setRankPoints(int rankPoints) {
        this.rankPoints = rankPoints;
    }

    @Override
    public int calculateXP(boolean isWin) {
        return isWin ? 200 : 50;
    }

    @Override
    public int calculateXP(boolean isWin, int kills) {
        return calculateXP(isWin) + (kills * 10);
    }

    @Override
    public void updateRank() {
        this.rankPoints += calculateXP(true);
        this.rankTier = "PLATINUM";
        System.out.println("Rank Updated: " + getUsername() + " is now " + rankTier + " (" + rankPoints + " pts)");
    }

    @Override
    public void displayProfile() {
        System.out.println("=== Ranked Player Card ===");
        System.out.println("User: " + getUsername() + " | Email: " + getEmail());
        System.out.println("Games: " + getGamesPlayed() + " | Wins: " + getWins() + " | Win Rate: " + String.format("%.2f", getWinRate()) + "%");
        System.out.println("Tier: " + rankTier + " | Rank Points: " + rankPoints);
    }
}

class ProPlayer extends RankedPlayer {
    private String sponsorName;
    private int tournamentWins;
    private double prizeMoneyEarned;

    public ProPlayer(String userId, String username, String email, int gamesPlayed, int wins, String rankTier, int rankPoints, String sponsorName, int tournamentWins, double prizeMoneyEarned) {
        super(userId, username, email, gamesPlayed, wins, rankTier, rankPoints);
        this.sponsorName = sponsorName;
        this.tournamentWins = tournamentWins;
        this.prizeMoneyEarned = prizeMoneyEarned;
    }

    @Override
    public int calculateXP(boolean isWin) {
        return isWin ? 300 : 100;
    }

    @Override
    public int calculateXP(boolean isWin, int kills) {
        return calculateXP(isWin) + (kills * 10);
    }

    @Override
    public void updateRank() {
        setRankPoints(getRankPoints() + calculateXP(true));
        System.out.println("Pro Rank Updated: " + getUsername() + " remains LEGENDARY (" + getRankPoints() + " pts, " + tournamentWins + " tournament wins)");
    }

    @Override
    public void displayProfile() {
        System.out.println("=== Pro Player Card ===");
        System.out.println("User: " + getUsername() + " | Games: " + getGamesPlayed() + " | Wins: " + getWins());
        System.out.println("Tier: " + getRankTier() + " | Rank Points: " + getRankPoints());
        System.out.println("Sponsor: " + sponsorName + " | Tournament Wins: " + tournamentWins);
        System.out.println("Prize Money: " + prizeMoneyEarned);
    }
}

class GameTitle {
    private String gameId;
    private String gameName;
    private String genre;

    public GameTitle(String gameId, String gameName, String genre) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.genre = genre;
    }

    public String getGameName() {
        return gameName;
    }

    public void displayGameInfo() {
        System.out.println("=== Game Title ===");
        System.out.println("ID: " + gameId + " | Name: " + gameName + " | Genre: " + genre);
    }
}

// --- Driver Class ---

public class GamingPlatform {
    public static void main(String[] args) {

        GameTitle gt = new GameTitle("G001", "ValorStrike", "FPS");

        RankedPlayer rp = new RankedPlayer(
            "U001", "ShadowX", "shadow@arena.com",
            120, 80, "Gold", 1500
        );

        ProPlayer pp = new ProPlayer(
            "U002", "NightHawk", "hawk@arena.com",
            500, 420, "Diamond", 4200,
            "RedBull", 12, 850000.0
        );

        gt.displayGameInfo();

        rp.displayProfile();
        System.out.println("XP (win) : " + rp.calculateXP(true));
        System.out.println("XP (win,15 kills): " + rp.calculateXP(true, 15));
        rp.updateRank();

        pp.displayProfile();
        System.out.println("Pro XP (win): " + pp.calculateXP(true));
        pp.updateRank();

        System.out.println("Total Users: " + GamePlayer.totalUsers);
    }
}