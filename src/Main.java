import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<Product> products = new ArrayList<>();
            ArrayList<String> urls = new ArrayList<>();
            ProductInfo info = new ProductInfo();
            ProductRank ranking = new ProductRank();
            Color background = Color.decode("#363737");
            Color gold = Color.decode("#CFB991");

            JFrame frame = new JFrame("BoilerShop");
            frame.getContentPane().setBackground(background);
            frame.setSize(1024,768);
            frame.setLayout(new GridLayout(6,1));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel productInput = new JPanel(new FlowLayout());
            productInput.setBackground(background);

            JPanel preferences = new JPanel(new FlowLayout());
            preferences.setBackground(background);

            JPanel buttons = new JPanel(new FlowLayout());
            buttons.setBackground(background);

            JTextField urlField = new JTextField(60);
            urlField.setPreferredSize(new Dimension(650, 45));
            urlField.setBorder(BorderFactory.createLineBorder(gold, 2));
            urlField.setBackground(background);
            urlField.setForeground(Color.WHITE);
            urlField.setCaretColor(Color.WHITE);

            JTextField budget = new JTextField(20);
            budget.setPreferredSize(new Dimension(180, 40));
            budget.setBorder(BorderFactory.createLineBorder(gold, 2));
            budget.setBackground(background);
            budget.setForeground(Color.WHITE);
            budget.setCaretColor(Color.WHITE);

            JTextField rating = new JTextField(20);
            rating.setPreferredSize(new Dimension(180, 40));
            rating.setBorder(BorderFactory.createLineBorder(gold,2));
            rating.setBackground(background);
            rating.setForeground(Color.WHITE);
            rating.setCaretColor(Color.WHITE);

            JLabel heading = new JLabel("BoilerShop");
            heading.setFont(new Font("Arial", Font.BOLD, 32));
            heading.setForeground(gold);
            heading.setHorizontalAlignment(JLabel.CENTER);

            JLabel productName = new JLabel("Product URL: ");
            productName.setForeground(Color.WHITE);

            JLabel budgetName = new JLabel("Budget: ");
            budgetName.setForeground(Color.WHITE);

            JLabel minRating = new JLabel("Minimum Rating: ");
            minRating.setForeground(Color.WHITE);

            JButton compareButton = new JButton("Compare");
            compareButton.setOpaque(true);
            compareButton.setBorderPainted(false);
            compareButton.setBackground(gold);
            compareButton.setForeground(Color.BLACK);

            JButton clearButton = new JButton("Clear");
            clearButton.setOpaque(true);
            clearButton.setBorderPainted(false);
            clearButton.setBackground(gold);
            clearButton.setForeground(Color.BLACK);

            JTextArea urlLinks = new JTextArea();
            urlLinks.setForeground(Color.WHITE);
            urlLinks.setBackground(background);
            urlLinks.setEditable(false);

            JTextArea results = new JTextArea();
            results.setForeground(Color.WHITE);
            results.setBackground(background);
            results.setEditable(false);

            JScrollPane productURLS = new JScrollPane(urlLinks);
            JScrollPane resultRank = new JScrollPane(results);

            productURLS.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(gold),
                    "Product Links", TitledBorder.CENTER, TitledBorder.TOP,
                    new Font("Arial", Font.BOLD, 16), gold));
            productURLS.setBackground(background);

            resultRank.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(gold),
                    "Rankings", TitledBorder.CENTER, TitledBorder.TOP,
                    new Font("Arial", Font.BOLD, 16), gold));
            resultRank.setBackground(background);

            productInput.add(productName);
            productInput.add(urlField);

            preferences.add(budgetName);
            preferences.add(budget);
            preferences.add(minRating);
            preferences.add(rating);

            buttons.add(compareButton);
            buttons.add(clearButton);

            urlField.addActionListener(e -> {
                if (!urlField.getText().isEmpty()) {
                    urls.add(urlField.getText());
                    urlLinks.append(urlField.getText() + "\n");
                    urlField.setText("");
                }
            });

            clearButton.addActionListener(e -> {
                urlField.setText("");
                budget.setText("");
                rating.setText("");
                urlLinks.setText("");
                results.setText("");

                urls.clear();
                products.clear();
            });

            compareButton.addActionListener(e -> {
                try {
                    double userBudget = -1.0;
                    double userRating = 0.0;

                    if (!budget.getText().isBlank()) {
                        userBudget = Double.parseDouble(budget.getText());
                    }

                    if (!rating.getText().isBlank()) {
                        userRating = Double.parseDouble(rating.getText());
                    }

                    Preferences pref = new Preferences(userBudget, userRating);
                    Gson gson = new Gson();

                    products.clear();
                    results.setText("");

                    for (String url : urls) {
                        String json = info.retrieveProduct(url);

                        if (json != null) {
                            JsonObject data = gson.fromJson(json, JsonObject.class);
                            JsonObject p = data.getAsJsonObject("product");

                            String name = p.get("title").getAsString();
                            String desc = p.get("description").getAsString();
                            double price = p.get("price").isJsonNull() ? -1 : p.get("price").getAsDouble();
                            double productRating = p.getAsJsonObject("rating").get("value").getAsDouble();
                            Product product = new Product(name, url, desc, price, productRating);

                            products.add(product);
                        }
                    }

                    ranking.rank(products, pref);

                    if (products.isEmpty()) {
                        results.setText("These products do not have what you are looking for. Try finding others!");
                    } else {
                        for (Product product : products) {
                            results.append(product.getName() + "\n\n");
                        }
                    }
                } catch (NumberFormatException ex) {
                    results.setText("Invalid Budget or rating");
                }
            });

            frame.add(heading);
            frame.add(productInput);
            frame.add(preferences);
            frame.add(buttons);
            frame.add(productURLS);
            frame.add(resultRank);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });
    }
}