package sample;

public class FiltersThread extends Thread{
    PanelMatching panelMatching;
    PanelOneTeamStats panelOneTeamStats;
    PanelConfrontation panelConfrontation;
    PanelTablesByLeague panelTablesByLeague;
    PanelTrends panelTrends;
    PanelShotsOnTarget panelShotsOnTarget;
    PanelPenalties panelPenalties;
    PanelAdvancedStatistics panelAdvancedStatistics;
    String league;
    String homeTeam;
    String awayTeam;


    public FiltersThread(PanelMatching panelMatching, PanelOneTeamStats panelOneTeamStats, PanelConfrontation panelConfrontation,
                         PanelTablesByLeague panelTablesByLeague, PanelTrends panelTrends, PanelShotsOnTarget panelShotsOnTarget,
                         PanelPenalties panelPenalties, PanelAdvancedStatistics panelAdvancedStatistics, String league, String homeTeam, String awayTeam){

        this.panelMatching = panelMatching;
        this.panelOneTeamStats = panelOneTeamStats;
        this.panelConfrontation = panelConfrontation;
        this.panelTablesByLeague = panelTablesByLeague;
        this.panelTrends = panelTrends;
        this.panelShotsOnTarget = panelShotsOnTarget;
        this.panelPenalties = panelPenalties;
        this.panelAdvancedStatistics = panelAdvancedStatistics;
        this.league = league;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

    }

    @Override
    public void run(){
        panelMatching.setFilters(league, homeTeam, awayTeam);
        panelOneTeamStats.setFilters(league);
        panelConfrontation.setFilters(league, homeTeam, awayTeam);
        panelShotsOnTarget.setFilters(league);
        panelTablesByLeague.setFilters(league);
        panelTrends.setFilters(league);
        panelPenalties.setFilters(league);
        panelAdvancedStatistics.setFilters(league, homeTeam, awayTeam);
    }
}