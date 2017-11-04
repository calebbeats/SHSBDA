package controller;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import model.GameFigure;

public class Quadtree {

    private final static int MAX_LEVELS = 5, MAX_OBJECTS = 5;

    private final int level;
    private final Rectangle node;
    private final List<Quadtree> nodeList;
    private final List<GameFigure> gameFigureList;

    public Quadtree(int level, Rectangle node) {
        this.level = level;
        this.node = node;
        nodeList = new ArrayList<>();
        gameFigureList = new ArrayList<>();
    }

    public synchronized void clear() {
        gameFigureList.clear();
        nodeList.clear();
    }

    public synchronized void insert(GameFigure gameFigure) {
        if (!nodeList.isEmpty()) {
            int index = getIndex(gameFigure.object);

            if (index != -1) {
                nodeList.get(index).insert(gameFigure);
                return;
            }
        }

        gameFigureList.add(gameFigure);

        if (gameFigureList.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodeList.isEmpty()) {
                split();
            }

            int i = 0;
            while (i < gameFigureList.size()) {
                int index = getIndex(gameFigureList.get(i).object);
                if (index != -1) {
                    nodeList.get(index).insert(gameFigureList.remove(i));
                } else {
                    i++;
                }
            }
        }
    }

    public synchronized List retrieve(List collidableObjectList, GameFigure gameFigure) {
        int index = getIndex(gameFigure.object);

        if (index != -1 && !nodeList.isEmpty()) {
            nodeList.get(index).retrieve(collidableObjectList, gameFigure);
        }

        gameFigureList.remove(gameFigure);
        collidableObjectList.addAll(gameFigureList);
        return collidableObjectList;
    }

    public synchronized void render(Graphics2D g2) {
        g2.draw(node);

        nodeList.forEach(node -> {
            node.render(g2);
        });
    }

    private synchronized void split() {
        int subWidth = (int) node.getWidth() / 2, subHeight = (int) node.getHeight() / 2;

        nodeList.add(new Quadtree(level + 1, new Rectangle(node.x + subWidth, node.y, subWidth, subHeight)));
        nodeList.add(new Quadtree(level + 1, new Rectangle(node.x, node.y, subWidth, subHeight)));
        nodeList.add(new Quadtree(level + 1, new Rectangle(node.x, node.y + subHeight, subWidth, subHeight)));
        nodeList.add(new Quadtree(level + 1, new Rectangle(node.x + subWidth, node.y + subHeight, subWidth,
                subHeight)));
    }

    private synchronized int getIndex(Rectangle object) {
        boolean topQuadrant = (new Rectangle(node.x, node.y, (int) node.getWidth(), (int) node.getHeight() / 2))
                .contains(object),
                bottomQuadrant = (new Rectangle(node.x, (int) node.getCenterY(), (int) node.getWidth(),
                        (int) node.getHeight() / 2)).contains(object),
                leftQuadrant = (new Rectangle(node.x, node.y, (int) node.getWidth() / 2, (int) node.getHeight())).contains(object),
                rightQuadrant = (new Rectangle((int) node.getCenterX(), node.y, (int) node.getWidth() / 2, (int) node.getHeight())).contains(object);

        if (topQuadrant) {
            if (rightQuadrant) {
                return 0;
            } else if (leftQuadrant) {
                return 1;
            }
        } else if (bottomQuadrant) {
            if (leftQuadrant) {
                return 2;
            } else if (rightQuadrant) {
                return 3;
            }
        }
        return -1;
    }
}
