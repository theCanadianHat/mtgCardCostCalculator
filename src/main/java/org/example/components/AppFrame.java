package org.example.components;

import org.example.model.scryfall.ScryFallMtgCard;
import org.example.model.scryfall.ScryFallMtgCardResponse;
import org.example.model.scryfall.ScryFallMtgSet;
import org.example.service.scryfall.ScyFallApi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class AppFrame extends JFrame {
    public static final String APP_TITLE = "Something Co0l";
    public static final int STARTING_WIDTH = 600;
    public static final int STARTING_HEIGHT = 700;

    private JLabel loadingSets;
    private JList<String> setList;
    private JButton loadCards;
    private ScyFallApi scyFallApi = new ScyFallApi();
    private JPanel panel;
    private JLabel selectedCardLabel;
    private List<ScryFallMtgSet> sets;
    private List<ScryFallMtgCard> selectedSetCards;
    public AppFrame(){
        setupFrame();
        setupPanel();
        addSetOptions(scyFallApi.getSets().getData());
    }

    private void setupFrame(){
        setSize(STARTING_WIDTH, STARTING_HEIGHT);
        setTitle(APP_TITLE);
        setupMenuBar();

        setVisible(true);
//        setLayout(new GridBagLayout());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void setupMenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem fileClose = new JMenuItem("Quit");
        fileClose.addActionListener(e -> System.exit(0));

        fileMenu.add(fileClose);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void setupPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        loadingSets = new JLabel("Loading Sets...");
        panel.add(loadingSets, BorderLayout.CENTER);
        add(panel);
        setVisible(true);
    }

    public void removeLoadingSets() {
        this.panel.remove(loadingSets);
    }

    public void addSetOptions(java.util.List<ScryFallMtgSet> sets) {
        this.sets = sets;
        removeLoadingSets();
        DefaultListModel<String> setListModel = new DefaultListModel<>();
//        setList.setBounds(25, 50, STARTING_WIDTH/2, STARTING_HEIGHT - 80);
        for (ScryFallMtgSet set : sets) {
            setListModel.addElement(set.getName());
        }
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        JLabel listLabel = new JLabel("Sets:");
        listLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        setList = new JList<>(setListModel);
        setList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(loadCards != null) {
                    loadCards.setEnabled(true);
                }
            }
        });
        JScrollPane listScrollPane = new JScrollPane(setList);
        listPanel.add(listLabel, BorderLayout.NORTH);
        listPanel.add(listScrollPane, BorderLayout.CENTER);

        this.panel.add(listPanel, BorderLayout.WEST);


        loadCards = new JButton("Load Cards");
        loadCards.setEnabled(false);
        loadCards.addActionListener(e -> {
            if(loadCards.isEnabled()) {
                ScryFallMtgSet selectedSet = sets.get(setList.getSelectedIndex());
                ScryFallMtgCardResponse response = scyFallApi.getCardsFromSet(selectedSet);
                selectedSetCards = response.getData();
                DefaultListModel<String> cardListModel = new DefaultListModel<>();
                for(ScryFallMtgCard card : response.getData()) {
                    cardListModel.addElement(card.getName());
                }
                JList<String> cardList = new JList<>(cardListModel);

                JScrollPane cardListPane = new JScrollPane(cardList);
                JPanel cardListPanel = new JPanel(new BorderLayout());
                cardListPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
                JLabel cardListLabel = new JLabel("Cards in " + selectedSet.getName());
                cardListPanel.add(cardListLabel, BorderLayout.NORTH);
                cardListPanel.add(cardListPane, BorderLayout.CENTER);
                selectedCardLabel = new JLabel("You loaded the cards");
                cardListPanel.add(selectedCardLabel, BorderLayout.SOUTH);
                panel.add(cardListPanel, BorderLayout.EAST);
                cardList.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        ScryFallMtgCard selectedCard = selectedSetCards.get(cardList.getSelectedIndex());
                        StringBuilder sb = new StringBuilder();
                        sb.append("#: ").append(selectedCard.getCollectorNumber()).append("\n");
                        sb.append("Rarity: ").append(selectedCard.getRarity()).append("\n");
                        sb.append("Oracle Text: ").append(selectedCard.getOracleText()).append("\n");
                        sb.append("Prices:").append("\n");
                        sb.append("\tEuro: €").append(selectedCard.getPrices().getEur()).append("\n");
                        sb.append("\tUSA: $").append(selectedCard.getPrices().getUsd()).append("\n");
                        sb.append("\tUSA Foil: $").append(selectedCard.getPrices().getUsd_foil()).append("\n");
//                        selectedCardLabel.setText(sb.toString());
                        JTextPane textPane = new JTextPane();
                        textPane.setText(sb.toString());
                        textPane.setSize(300, 150);
                        cardListPanel.remove(selectedCardLabel);
                        cardListPanel.add(textPane, BorderLayout.SOUTH);

                        cardListPanel.updateUI();
                    }
                });
                panel.updateUI();
            }
        });
//        loadCards.setBounds(300, 30, 80, 30);
        this.panel.add(loadCards, BorderLayout.SOUTH);
        setVisible(true);
    }
}
