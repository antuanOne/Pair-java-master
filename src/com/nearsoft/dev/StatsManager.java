package com.nearsoft.dev;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Usuario on 08/07/2016.
 */
public class StatsManager {


    List<TeamStaticsDataTransfer> staticsList;

    public void loadStatsFromFile(String file) {

        String workingDir = System.getProperty("user.dir");
        String path = workingDir + "\\src\\com\\nearsoft\\dev\\test\\" + file;
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(path);
            doc.normalizeDocument();

            NodeList nList = doc.getElementsByTagName("matchResult");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            staticsList = new ArrayList<TeamStaticsDataTransfer>();
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    TeamStaticsDataTransfer teamStats = new TeamStaticsDataTransfer();
                    Element element = (Element) node;
                    String dateGame = element.getElementsByTagName("date").item(0).getTextContent();
                    teamStats.setGameDate(df.parse(dateGame));
                    teamStats.setLocalTeam(element.getElementsByTagName("local").item(0).getTextContent());
                    teamStats.setVisitorTeam(element.getElementsByTagName("visitor").item(0).getTextContent());
                    String score = element.getElementsByTagName("score").item(0).getTextContent();
                    String[] scoreArray = score.split("-");
                    teamStats.setLocalScore(new Integer(scoreArray[0]));
                    teamStats.setVisitorScore(new Integer(scoreArray[1]));
                    staticsList.add(teamStats);
                }

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public String getTeamNameWithMostWon() {
        Map<String, SummaryStats> mapWins = getStatics();
        return getTeamWithHigherCountWinsFromMap(mapWins);
    }

    public String getTeamNameWithMostLost() {
        Map<String, SummaryStats> mapLost = getStatics();
        return getTeamWithHigherCountLostsFromMap(mapLost);
    }

    private Map<String, SummaryStats> getStatics() {
        Map<String, SummaryStats> mapResults = new HashMap<String, SummaryStats>();
        for (TeamStaticsDataTransfer teamStats : staticsList) {
            if (teamStats.getLocalScore() > teamStats.getVisitorScore()) {
                addOneToKeyInMap(mapResults, "win", teamStats.getLocalTeam());
                addOneToKeyInMap(mapResults, "lost", teamStats.getVisitorTeam());

            } else if (teamStats.getLocalScore() < teamStats.getVisitorScore()) {
                addOneToKeyInMap(mapResults, "win", teamStats.getVisitorTeam());
                addOneToKeyInMap(mapResults, "lost", teamStats.getLocalTeam());
            } else {
                addOneToKeyInMap(mapResults, "draw", teamStats.getVisitorTeam());
                addOneToKeyInMap(mapResults, "draw", teamStats.getLocalTeam());
            }
        }
        return mapResults;
    }

    private void addOneToKeyInMap(Map mapa, String tipo, String team) {
        SummaryStats sumStat;
        if (mapa.get(team) == null) {
            sumStat = new SummaryStats();
            if (tipo.equals("win")) {
                sumStat.setWons(sumStat.getWons() + 1);
            } else if (tipo.equals("lost")) {
                sumStat.setLost(sumStat.getLost() + 1);
            } else {
                sumStat.setDraws(sumStat.getDraws() + 1);
            }
        } else {
            sumStat = (SummaryStats) mapa.get(team);
            if (tipo.equals("win")) {
                sumStat.setWons(sumStat.getWons() + 1);
            } else if (tipo.equals("lost")) {
                sumStat.setLost(sumStat.getLost() + 1);
            } else {
                sumStat.setDraws(sumStat.getDraws() + 1);
            }

        }
        mapa.put(team, sumStat);
    }

    private String getTeamWithHigherCountWinsFromMap(Map map) {

        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable)
                        ((SummaryStats) ((Map.Entry) (o1)).getValue()).getWons()).compareTo(((SummaryStats) (((Map.Entry) (o2)).getValue())).getWons());
            }
        });
        Collections.reverse(list);
        Map.Entry entry = (Map.Entry) list.get(0);
        return entry.getKey().toString();
    }

    private String getTeamWithHigherCountLostsFromMap(Map map) {

        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable)
                        ((SummaryStats) ((Map.Entry) (o1)).getValue()).getLost()).compareTo(((SummaryStats) (((Map.Entry) (o2)).getValue())).getLost());
            }
        });
        Collections.reverse(list);
        Map.Entry entry = (Map.Entry) list.get(0);
        return entry.getKey().toString();
    }

    @Override
    public String toString() {
        Map<String, SummaryStats> map = getStatics();
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                SummaryStats stat1 = (SummaryStats) ((Map.Entry) (o1)).getValue();
                SummaryStats stat2 = (SummaryStats) ((Map.Entry) (o2)).getValue();
                int totalScore1 = (stat1.getWons() * 3) + (stat1.getDraws());
                int totalScore2 = (stat2.getWons() * 3) + (stat2.getDraws());

                if (totalScore1 > totalScore2) {
                    return 1;
                } else if (totalScore1 < totalScore2) {
                    return -1;
                } else {
                    if (stat1.getLost() < stat2.getLost()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
        Collections.reverse(list);
        StringBuilder sb = new StringBuilder();
        sb.append("#\tTeam\tWon\tDrawn\tLost");
        for (int x = 0; x < list.size(); x++) {
            Map.Entry entry = (Map.Entry) list.get(x);
            SummaryStats sumStat = map.get(entry.getKey().toString());
            sb.append(x + 1);
            sb.append("\t");
            sb.append(entry.getKey().toString());
            sb.append("\t");
            sb.append(sumStat.getWons());
            sb.append("\t");
            sb.append(sumStat.getDraws());
            sb.append("\t");
            sb.append(sumStat.getLost());
        }

        return sb.toString();

    }
}
