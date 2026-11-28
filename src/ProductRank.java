import java.util.ArrayList;

public class ProductRank {
    public void rank(ArrayList<Product> products, Preferences preference) {
        for (int i = 0; i < products.size(); i++) {
            if (!matchPreferences(products.get(i), preference)) {
                products.remove(i);
                i--;
            }
        }

        for (int i = 0; i < products.size(); i++) {
            for (int j = i + 1; j < products.size(); j++) {
                if (products.get(j).getRating() > products.get(i).getRating()) {
                    products.set(i, products.get(j));
                    products.set(j, products.get(i));
                }
            }
        }
    }

    private boolean matchPreferences(Product product, Preferences preference) {
        if (preference.getBudget() != -1 && product.getPrice() > preference.getBudget()) {
            return false;
        } else if (product.getRating() < preference.getMinimumRating()) {
            return false;
        } else {
            return true;
        }
    }
}