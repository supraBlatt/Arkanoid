package game.highscores;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Yuval Ezra
 * A highScores-table.
 */
public class HighScoresTable implements Serializable {

   private static final long serialVersionUID = -7962091275772613394L;
   private int size;
   private List<ScoreInfo> scores;

   /**
    * A constructor for HighScoresTable.
    * @param size the size of the high-scores table.
    */
   public HighScoresTable(int size) {
       this.size = size;
       this.scores = new LinkedList<ScoreInfo>();
   }

   /**
    * Add 'score' to this Table.
    * @param score the score to be added to this Table.
    */
   public void add(ScoreInfo score) {
       int rank = this.getRank(score.getScore());

       if (rank > this.size()) {
           return;
       }

       // if full but need to add
       if (this.scores.size() == this.size()) {
           this.scores.remove(this.scores.size() - 1);
       }

       this.scores.add(rank - 1, score);
   }

   /**
    * @return this Table's size.
    */
   public int size() {
       return this.size;
   }

   /**
    * sets this Table's size to 'size'.
    * @param s this Table's new size.
    */
   public void setSize(int s) {
       this.size = s;
   }

   /**
    * @return the current high-scores.
    */
   public List<ScoreInfo> getHighScores() {
       return this.scores;
   }

   /**
    * Return the 'rank' of the current score : where will it be on the
    * list if added?
    * Rank 1 means the score will be the highest on the list, and rank
    * 'size' means it would be the lowest. Rank > 'size' means the score
    * is too low and will not be added to the list.
    * @param score the score to be added to this HighScoresTable.
    * @return this scores 'rank' in the Table.
    */
   public int getRank(int score) {
       for (int i = 0; i < this.scores.size(); i++) {
           if (this.scores.get(i).getScore() < score) {
               return i + 1;
           }
       }

       // the end of the list
       return this.scores.size() + 1;
   }

   /**
    * Clears this Table.
    */
   public void clear() {
       this.scores.clear();
   }

   /**
    * Loads Table data from File.
    * @param filename the File to load this Table's data from.
    * @throws IOException if there was a problem reading fomr the file.
    */
   public void load(File filename) throws IOException {

       FileInputStream in = null;
       try {
           in = new FileInputStream(filename);
           ObjectInputStream reader = new ObjectInputStream(in);
           HighScoresTable t = null;
           try {
               t = (HighScoresTable) reader.readObject();
           } catch (ClassNotFoundException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           } finally {
               reader.close();
           }

           this.clear();
           if (t != null) {
               this.scores.addAll(t.getHighScores());
           }
           if (this.size < this.scores.size()) {
               this.size = this.scores.size();
           }
       } catch (EOFException e) {

           // file is clear
           this.save(filename);
       } catch (IOException e) {
           System.out.println("Failed to load table.");
       } finally {
           if (in != null) {
               in.close();
           }
       }
   }

   /**
    * Saves Table data to the specified File.
    * @param filename the file to save this Table in.
    * @throws IOException if the File failed to open.
    */
   public void save(File filename) throws IOException {
       FileOutputStream out = null;
       try {
           out = new FileOutputStream(filename);
           ObjectOutputStream writer = new ObjectOutputStream(out);
           writer.writeObject(this);
           writer.close();
       } catch (IOException e) {
           System.out.println("Failed saving in the file.");
       } finally {
           if (out != null) {
               out.close();
           }
       }
   }

   @Override
    public String toString() {
        return this.scores.toString();
    }

   /**
    * Loads a Table from File, and returns it. If the File does not exist
    * or there was a problem with reading it, returns an empty table.
    * @param filename the File to load the Table from.
    * @return a new Table loaded from 'filename'.
    */
   public static HighScoresTable loadFromFile(File filename) {
       HighScoresTable scores = new HighScoresTable(5);
       try {
           scores.load(filename);
       } catch (Exception e) {
           System.out.println("Failed to load from file.");
           return scores;
       }
       return scores;
   }
}