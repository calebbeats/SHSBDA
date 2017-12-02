package controller;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import model.GameFigure;

/**
 * Quadtree is implemented with the help from this tutorial with some
 * modifications
 * https://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374
 *
 * @author hungt
 */
public class Quadtree {

    /**
     * MAX_LEVELS defines how many levels can quadtree be divided MAX_OBJECTS
     * defines how many objects a node can hold before splitting
     */
    private final static int MAX_LEVELS = 5, MAX_OBJECTS = 5;

    private final int level;
    private final Rectangle node;
    private final List<Quadtree> nodeList;
    private final List<GameFigure> gameFigureList;

    private boolean display = true;

    public Quadtree(int level, Rectangle node) {
        this.level = level;
        this.node = node;
        nodeList = new ArrayList<>();
        gameFigureList = new ArrayList<>();
    }

    /**
     * Clear the nodeList for new insertion
     */
    public synchronized void clear() {
        gameFigureList.clear();
        nodeList.clear();
    }

    /**
     * Insert objects into appropriate node
     *
     * @param gameFigure
     */
    public synchronized void insert(GameFigure gameFigure) {

        // Get the quadrant of the node where the gameFigure should be inserted
        if (!nodeList.isEmpty()) {
            int index = getIndex(new Point((int) gameFigure.x,
                    (int) gameFigure.y));

            if (index != -1) {
                nodeList.get(index).insert(gameFigure);
                return;
            }
        }

        gameFigureList.add(gameFigure);

        // Insert all possible gameFigures into the node specified by the index
        if (gameFigureList.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodeList.isEmpty()) {
                split();
            }

            int i = 0;
            while (i < gameFigureList.size()) {
                Point point = new Point((int) gameFigureList.get(i).x,
                        (int) gameFigureList.get(i).y);
                int index = getIndex(point);
                if (index != -1) {
                    nodeList.get(index).insert(gameFigureList.remove(i));
                } else {
                    i++;
                }
            }
        }
    }

    /**
     * Determines collidable objects based on location of gameFigure
     *
     * @param collidableObjectList
     * @param gameFigure
     * @return List of collidableObjectList
     */
    public synchronized List retrieve(List collidableObjectList, GameFigure gameFigure) {
        // Get the index of the node that gameFigure may be collide with other objects
        int index = getIndex(new Point((int) gameFigure.x, (int) gameFigure.y));

        // Get all the objects in the node specified by the index
        if (index != -1 && !nodeList.isEmpty()) {
            nodeList.get(index).retrieve(collidableObjectList, gameFigure);
        }

        gameFigureList.remove(gameFigure);
        collidableObjectList.addAll(gameFigureList);
        return collidableObjectList;
    }

    /**
     * Toggle to display quadtree in action
     */
    public void toggleDisplay() {
        display = !display;
    }

    /**
     * Render the grid on canvas to show how quadtree works
     *
     * @param g2
     */
    public synchronized void render(Graphics2D g2) {
        if (display) {
            g2.draw(node);

            nodeList.forEach(node -> {
                node.render(g2);
            });
        }
    }

    /**
     * Split current node into four subnodes
     */
    private synchronized void split() {
        int subWidth = (int) node.getWidth() / 2, subHeight = (int) node.getHeight() / 2;

        nodeList.add(new Quadtree(level + 1, new Rectangle(node.x + subWidth, node.y, subWidth, subHeight)));
        nodeList.add(new Quadtree(level + 1, new Rectangle(node.x, node.y, subWidth, subHeight)));
        nodeList.add(new Quadtree(level + 1, new Rectangle(node.x, node.y + subHeight, subWidth, subHeight)));
        nodeList.add(new Quadtree(level + 1, new Rectangle(node.x + subWidth, node.y + subHeight, subWidth,
                subHeight)));
    }

    /**
     * Determines the index of the subnode that object is in
     *
     * @param object
     * @return Index of the subnode
     */
    private synchronized int getIndex(Point object) {
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
