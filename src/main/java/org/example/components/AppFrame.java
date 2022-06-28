package org.example.components;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class AppFrame extends Frame {
    public static final String APP_TITLE = "Something Co0l";
    public static final int STARTING_WIDTH = 400;
    public static final int STARTING_HEIGHT = 500;

    private Label loadingSets;
    private List setList;
    private Button loadCards;
    private GridBagConstraints gridBagConstraints;
    public AppFrame(){
        setupFrame();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        loadingSets = new Label("Loading Sets...");
        loadingSets.setBounds(30, 25, 80, 30);
        add(loadingSets, gridBagConstraints);

    }

    private void setupFrame(){
        setSize(STARTING_WIDTH, STARTING_HEIGHT);
        setTitle(APP_TITLE);
        setLayout(new GridBagLayout());
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        gridBagConstraints = new GridBagConstraints();
    }

    public void removeLoadingSets() {
        this.remove(loadingSets);
    }

    public void addSetOptions(Map<String, Object> sets) {
        ArrayList<Map<String, Object>> dataList = (ArrayList<Map<String, Object>>) sets.get("data");
        setList = new List(dataList.size());
//        setList.setBounds(25, 50, STARTING_WIDTH/2, STARTING_HEIGHT - 80);
        for (Map<String, Object> set : dataList) {
            setList.add((String) set.get("name"));
        }
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        this.add(setList, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        loadCards = new Button("Load Cards");
//        loadCards.setBounds(300, 30, 80, 30);
        this.add(loadCards, gridBagConstraints);
    }
}
