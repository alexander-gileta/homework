import java.util.PriorityQueue;

interface StockMarket {
    void add(Stock stock);
    void remove(Stock stock);
    Stock mostValuableStock();
}

class Stock {
    private String name;
    private double price;

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " (" + price + ")";
    }
}

class StockMarketImpl implements StockMarket {
    private PriorityQueue<Stock> stockQueue;

    public StockMarketImpl() {
        stockQueue = new PriorityQueue<>((stock1, stock2) -> Double.compare(stock2.getPrice(), stock1.getPrice()));
    }

    @Override
    public void add(Stock stock) {
        stockQueue.offer(stock);
    }

    @Override
    public void remove(Stock stock) {
        stockQueue.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return stockQueue.peek();
    }

    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarketImpl();
        
        Stock apple = new Stock("Apple", 150.0);
        Stock google = new Stock("Google", 200.0);
        Stock amazon = new Stock("Amazon", 180.0);

        stockMarket.add(apple);
        stockMarket.add(google);
        stockMarket.add(amazon);

        System.out.println("Most valuable stock: " + stockMarket.mostValuableStock());

        stockMarket.remove(google);
        System.out.println("Most valuable stock after removal: " + stockMarket.mostValuableStock());
    }
}
