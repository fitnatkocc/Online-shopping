import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class ShoppingAppGUI {
    // Core models and services
    private ShoppingCart cart;
    private Wishlist wishlist;
    private SearchService searchService;
    private List<OrderRecord> orderRecords;

    // Stored user info
    private List<AddressRecord> addresses = new ArrayList<>();
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    // Main window
    private JFrame mainFrame;
    private JTabbedPane tabbedPane;

    // UI models
    private DefaultListModel<String> paymentItemsModel;
    private DefaultTableModel ordersTableModel;

    // Shared components
    private JList<Product> productList;
    private JList<Product> wishlistList;
    private JTable ordersTable;
    private JComboBox<String> categoryCombo;
    private JTextField searchField;           // <-- new
    private JComboBox<String> addressCombo;
    private JComboBox<String> cardCombo;

    // Panels
    private JPanel productsPanel;
    private JPanel wishlistPanel;
    private JPanel paymentPanel;
    private JPanel ordersPanel;
    private JPanel userInfoPanel;

    // Categories
    private static final String[] CATEGORIES = {
        "Electronics",
        "Clothing & Accessories",
        "Home & Kitchen",
        "Beauty & Personal Care",
        "Sports & Outdoors",
        "Books & Stationery",
        "Toys & Games",
        "Health & Wellness",
        "Pet Supplies"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShoppingAppGUI().showLogin());
    }

    private void showLogin() {
        JDialog dlg = new JDialog((Frame)null, "Login", true);
        dlg.setSize(300,150);
        dlg.setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridLayout(3,2,5,5));
        p.setBackground(new Color(220,220,220));
        p.add(new JLabel("Username:"));
        JTextField uf = new JTextField(); p.add(uf);
        p.add(new JLabel("Password:"));
        JPasswordField pf = new JPasswordField(); p.add(pf);
        p.add(new JLabel());
        JButton b = new JButton("Login"); p.add(b);

        b.addActionListener(e -> {
            if ("admin".equals(uf.getText()) &&
                "admin".equals(new String(pf.getPassword()))) {
                dlg.dispose();
                initialize();
            } else {
                JOptionPane.showMessageDialog(dlg,
                    "Invalid credentials","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.add(p);
        dlg.setVisible(true);
    }

    private void initialize() {
        cart           = new ShoppingCart();
        wishlist       = new Wishlist();
        searchService  = new SearchService();
        orderRecords   = new ArrayList<>();
        paymentItemsModel = new DefaultListModel<>();

        mainFrame = new JFrame("Online Shopping System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(960,680);
        mainFrame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 2) refreshPaymentItems();
        });

        createProductsPanel();
        createWishlistPanel();
        createPaymentPanel();
        createOrdersPanel();
        createUserInfoPanel();

        tabbedPane.addTab("Products", productsPanel);
        tabbedPane.addTab("Wishlist", wishlistPanel);
        tabbedPane.addTab("Payment", paymentPanel);
        tabbedPane.addTab("My Orders", ordersPanel);
        tabbedPane.addTab("User Info", userInfoPanel);

        mainFrame.add(tabbedPane);
        mainFrame.setVisible(true);
    }

    private void styleButton(JButton b, Color bg) {
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setBorderPainted(false);
    }
    private void styleCombo(JComboBox<?> c) {
        c.setBackground(new Color(240,240,240));
    }

    // -------- Products Tab --------
    private void createProductsPanel() {
        productsPanel = new JPanel(new BorderLayout(10,10));
        productsPanel.setBackground(new Color(205,230,255));

        // Top: Category + Search
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryCombo = new JComboBox<>(CATEGORIES);
        styleCombo(categoryCombo);
        searchField = new JTextField(15);                  // <-- new
        top.add(new JLabel("Category:")); top.add(categoryCombo);
        top.add(new JLabel("Search:"));   top.add(searchField);
        productsPanel.add(top, BorderLayout.NORTH);

        // Product list
        DefaultListModel<Product> pm = new DefaultListModel<>();
        productList = new JList<>(pm);
        productList.setBackground(new Color(230,245,255));
        productList.setCellRenderer(new DefaultListCellRenderer(){
            @Override public Component getListCellRendererComponent(JList<?> list,
                                                                      Object value,
                                                                      int index,
                                                                      boolean isSelected,
                                                                      boolean cellHasFocus) {
                super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                if (value instanceof Product) {
                    Product p = (Product)value;
                    setText(String.format("%s - $%.2f (stock: %d)",
                          p.getName(), p.getPrice(), p.getStock()));
                }
                return this;
            }
        });

        // Details pane on right
        JPanel details = new JPanel(new BorderLayout(10,10));
        details.setBackground(new Color(205,230,255));
        details.setBorder(BorderFactory.createTitledBorder("Product Details"));
        JPanel info = new JPanel(new GridLayout(3,1));
        info.setOpaque(false);
        JLabel ln = new JLabel("Name: ");
        JLabel pr = new JLabel("Price: ");
        JLabel st = new JLabel("Stock: ");
        info.add(ln); info.add(pr); info.add(st);
        details.add(info, BorderLayout.NORTH);
        JTextArea da = new JTextArea();
        da.setLineWrap(true); da.setWrapStyleWord(true); da.setEditable(false);
        details.add(new JScrollPane(da), BorderLayout.CENTER);

        // List selection → show details
        productList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Product p = productList.getSelectedValue();
                if (p != null) {
                    ln.setText("Name:  " + p.getName());
                    pr.setText(String.format("Price: $%.2f", p.getPrice()));
                    st.setText("Stock: " + p.getStock());
                    da.setText(p.getDescription());
                }
            }
        });

        // Split pane
        JSplitPane split = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(productList),
            details
        );
        split.setDividerLocation(300);
        productsPanel.add(split, BorderLayout.CENTER);

        // Buttons
        JPanel btns = new JPanel();
        btns.setBackground(new Color(205,230,255));
        JButton atc = new JButton("Add to Cart");
        JButton atw = new JButton("Add to Wishlist");
        styleButton(atc,new Color(0,120,215));
        styleButton(atw,new Color(0,150,0));
        btns.add(atc); btns.add(atw);
        productsPanel.add(btns, BorderLayout.SOUTH);

        atc.addActionListener(e -> {
            Product p = productList.getSelectedValue();
            if (p != null) {
                cart.addItem(p, 1, "");
                JOptionPane.showMessageDialog(mainFrame, p.getName()+" added to cart.");
            }
        });
        atw.addActionListener(e -> {
            Product p = productList.getSelectedValue();
            if (p != null) {
                wishlist.addProduct(p);
                refreshWishlist();
            }
        });

        // Wire category & search into refreshProductList()
        categoryCombo.addActionListener(e -> refreshProductList());
        searchField.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){ refreshProductList(); }
            public void removeUpdate(DocumentEvent e){ refreshProductList(); }
            public void changedUpdate(DocumentEvent e){ refreshProductList(); }
        });

        // initial fill
        refreshProductList();
    }

    /** Repopulates productList model according to category + searchField */
    private void refreshProductList() {
    String cat  = (String)categoryCombo.getSelectedItem();
    String term = searchField.getText().trim().toLowerCase();

    DefaultListModel<Product> mdl = 
        (DefaultListModel<Product>)productList.getModel();
    mdl.clear();

    // If there's a search term, ignore category and search everything:
    List<Product> source = term.isEmpty()
        ? searchService.filterByCategory(cat)
        : searchService.getAllProducts();

    for (Product p : source) {
        if (!Stock.isAvailable(p)) continue;

        // Always match name against the term
        if (term.isEmpty() || p.getName().toLowerCase().contains(term)) {
            mdl.addElement(p);
        }
    }
}

    // -------- Wishlist Tab --------
    private void createWishlistPanel() {
        wishlistPanel = new JPanel(new BorderLayout(10,10));
        wishlistPanel.setBackground(new Color(205,255,205));

        DefaultListModel<Product> wm = new DefaultListModel<>();
        wishlistList = new JList<>(wm);
        wishlistList.setBackground(new Color(230,255,230));
        wishlistList.setCellRenderer(productList.getCellRenderer());
        wishlistPanel.add(new JScrollPane(wishlistList), BorderLayout.CENTER);

        // Details & buttons same pattern as Products
        JPanel details = new JPanel(new BorderLayout(10,10));
        details.setBackground(new Color(205,255,205));
        details.setBorder(BorderFactory.createTitledBorder("Product Details"));
        JPanel info = new JPanel(new GridLayout(3,1));
        JLabel ln=new JLabel("Name: "), pr=new JLabel("Price: "), st=new JLabel("Stock: ");
        info.setOpaque(false);
        info.add(ln); info.add(pr); info.add(st);
        details.add(info,BorderLayout.NORTH);
        JTextArea da=new JTextArea();
        da.setLineWrap(true); da.setWrapStyleWord(true); da.setEditable(false);
        details.add(new JScrollPane(da),BorderLayout.CENTER);
        wishlistList.addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()){
                Product p=wishlistList.getSelectedValue();
                if(p!=null){
                    ln.setText("Name:  "+p.getName());
                    pr.setText(String.format("Price: $%.2f",p.getPrice()));
                    st.setText("Stock: "+p.getStock());
                    da.setText(p.getDescription());
                }
            }
        });
        wishlistPanel.add(details,BorderLayout.EAST);

        JPanel btns=new JPanel();
        btns.setBackground(new Color(205,255,205));
        JButton atc=new JButton("Add to Cart"), rm=new JButton("Remove");
        styleButton(atc,new Color(0,120,215)); styleButton(rm,new Color(200,0,0));
        btns.add(atc); btns.add(rm);
        wishlistPanel.add(btns,BorderLayout.SOUTH);

        atc.addActionListener(e->{
            Product p=wishlistList.getSelectedValue();
            if(p!=null){ cart.addItem(p,1,""); JOptionPane.showMessageDialog(mainFrame,p.getName()+" added to cart."); }
        });
        rm.addActionListener(e->{
            Product p=wishlistList.getSelectedValue();
            if(p!=null){ wishlist.removeProduct(p); refreshWishlist(); }
        });
    }
    private void refreshWishlist(){
        DefaultListModel<Product> m=(DefaultListModel<Product>)wishlistList.getModel();
        m.clear();
        for(Product p: wishlist.viewWishlist()) m.addElement(p);
    }

    // -------- Payment Tab --------
    private void createPaymentPanel(){
        paymentPanel=new JPanel(new BorderLayout(10,10));
        paymentPanel.setBackground(new Color(255,245,205));
        paymentItemsModel=new DefaultListModel<>();
        JList<String> sumList=new JList<>(paymentItemsModel);
        sumList.setBackground(new Color(255,250,230));

        JPanel details=new JPanel(new BorderLayout(10,10));
        details.setBorder(BorderFactory.createTitledBorder("Item Details"));
        details.setBackground(new Color(255,245,205));
        JPanel info=new JPanel(new GridLayout(3,1));
        JLabel nn=new JLabel("Name: "), pp=new JLabel("Price: "), qq=new JLabel("Qty: ");
        info.add(nn); info.add(pp); info.add(qq);
        details.add(info,BorderLayout.NORTH);
        JTextArea da=new JTextArea();
        da.setLineWrap(true); da.setWrapStyleWord(true); da.setEditable(false);
        details.add(new JScrollPane(da),BorderLayout.CENTER);

        sumList.addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()){
                int i=sumList.getSelectedIndex();
                if(i>=0 && i<cart.getItems().size()){
                    CartItem ci=cart.getItems().get(i);
                    Product p=ci.getProduct();
                    nn.setText("Name: "+p.getName());
                    pp.setText(String.format("Price: $%.2f",p.getPrice()));
                    qq.setText("Qty: "+ci.getQuantity());
                    da.setText(p.getDescription());
                }
            }
        });

        JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(sumList), details);
        split.setDividerLocation(300);
        paymentPanel.add(split, BorderLayout.CENTER);

        JPanel top=new JPanel(new GridLayout(2,1,5,5));
        top.setBackground(new Color(255,245,205));
        JPanel ap=new JPanel(new FlowLayout(FlowLayout.LEFT));
        ap.setBackground(new Color(255,245,205));
        ap.setBorder(BorderFactory.createTitledBorder("Shipping Address"));
        addressCombo=new JComboBox<>();
        styleCombo(addressCombo);
        JButton addrBtn=new JButton("Add New Addr");
        styleButton(addrBtn,new Color(180,100,0));
        ap.add(new JLabel("Address:")); ap.add(addressCombo); ap.add(addrBtn);
        top.add(ap);

        JPanel pi=new JPanel(new FlowLayout(FlowLayout.LEFT));
        pi.setBackground(new Color(255,245,205));
        pi.setBorder(BorderFactory.createTitledBorder("Payment Method"));
        cardCombo=new JComboBox<>();
        styleCombo(cardCombo);
        JButton cardBtn=new JButton("Add New Card");
        styleButton(cardBtn,new Color(180,100,0));
        pi.add(new JLabel("Card:")); pi.add(cardCombo); pi.add(cardBtn);
        top.add(pi);

        paymentPanel.add(top,BorderLayout.NORTH);

        addrBtn.addActionListener(e->tabbedPane.setSelectedIndex(4));
        cardBtn.addActionListener(e->tabbedPane.setSelectedIndex(4));

        JButton cb=new JButton("Confirm Order");
        styleButton(cb,new Color(0,150,0));
        JPanel bot=new JPanel();
        bot.setBackground(new Color(255,245,205));
        bot.add(cb);
        paymentPanel.add(bot,BorderLayout.SOUTH);

        cb.addActionListener(e->{
            if(addressCombo.getItemCount()==0 || cardCombo.getItemCount()==0){
                JOptionPane.showMessageDialog(mainFrame,
                    "Please add/select address and card.","Missing Info",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Build items string and decrement stock
            List<CartItem> items = new ArrayList<>(cart.getItems());
            String itemsStr = items.stream()
                .map(ci->ci.getProduct().getName()+" x"+ci.getQuantity())
                .collect(Collectors.joining(", "));
            items.forEach(ci->{
                Product p=ci.getProduct();
                p.setStock(p.getStock() - ci.getQuantity());
            });

            // Record the order
            long rnd = (long)(Math.random()*90000000000L)+10000000000L;
            String orderNo = Long.toString(rnd);
            orderRecords.add(new OrderRecord(orderNo, itemsStr));

            // Show in My Orders table
            ordersTableModel.addRow(new Object[]{
                orderNo, itemsStr, true,false,false,false
            });
            int row = ordersTableModel.getRowCount()-1;
            new Timer(5000,ev->ordersTableModel.setValueAt(true,row,2)).start();
            new Timer(10000,ev->ordersTableModel.setValueAt(true,row,3)).start();
            new Timer(15000,ev->ordersTableModel.setValueAt(true,row,4)).start();

            JOptionPane.showMessageDialog(mainFrame,
                "Order placed! No: "+orderNo);

            cart.clearCart();
            paymentItemsModel.clear();
            refreshWishlist();
            refreshProductList();    // <-- update stock in the product list
            tabbedPane.setSelectedIndex(3);
        });
    }

    // -------- My Orders Tab --------
    private void createOrdersPanel() {
        ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.setBackground(new Color(255,215,215));

        ordersTableModel = new DefaultTableModel(
            new String[]{"Order No","Items","Received","Preparing","Shipped","Delivered"},
            0
        ) {
            @Override public Class<?> getColumnClass(int c) {
                if (c < 2) return String.class;
                return Boolean.class;
            }
            @Override public boolean isCellEditable(int r,int c){return false;}
        };

        ordersTable = new JTable(ordersTableModel);
        ordersTable.setRowHeight(30);
        ordersTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        ordersTable.setDefaultRenderer(Boolean.class, new StatusCellRenderer());

        ordersPanel.add(new JScrollPane(ordersTable), BorderLayout.CENTER);
    }

    // -------- User Info Tab --------
    private void createUserInfoPanel() {
        userInfoPanel = new JPanel(new GridBagLayout());
        userInfoPanel.setBackground(new Color(215,235,255));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx=0; c.gridy=0; c.gridwidth=2;
        userInfoPanel.add(new JLabel("Manage Addresses"),c);

        c.gridwidth=1; c.gridy=1;
        userInfoPanel.add(new JLabel("Title:"),c);
        JTextField addrTitle=new JTextField(10);
        c.gridx=1; userInfoPanel.add(addrTitle,c);

        c.gridx=0; c.gridy=2;
        userInfoPanel.add(new JLabel("Address:"),c);
        JTextField addrField=new JTextField(20);
        c.gridx=1; userInfoPanel.add(addrField,c);

        JButton addAddr=new JButton("Add Address");
        styleButton(addAddr,new Color(180,0,180));
        c.gridx=0; c.gridy=3; c.gridwidth=2;
        userInfoPanel.add(addAddr,c);

        c.gridwidth=1; c.gridy=4;
        userInfoPanel.add(new JLabel("Manage Cards"),c);
        c.gridy=5;
        userInfoPanel.add(new JLabel("Title:"),c);

        JTextField ct=new JTextField(10);
        c.gridx=1; userInfoPanel.add(ct,c);

        c.gridx=0; c.gridy=6;
        userInfoPanel.add(new JLabel("Card #:"),c);
        JTextField cn=new JTextField(16);
        c.gridx=1; userInfoPanel.add(cn,c);

        c.gridx=0; c.gridy=7;
        userInfoPanel.add(new JLabel("Expiry (MM/YY):"),c);
        JTextField ex=new JTextField(5);
        c.gridx=1; userInfoPanel.add(ex,c);

        c.gridx=0; c.gridy=8;
        userInfoPanel.add(new JLabel("CVV:"),c);
        JTextField cv=new JTextField(3);
        c.gridx=1; userInfoPanel.add(cv,c);

        JButton addCard=new JButton("Add Card");
        styleButton(addCard,new Color(180,0,180));
        c.gridx=0; c.gridy=9; c.gridwidth=2;
        userInfoPanel.add(addCard,c);

        addAddr.addActionListener(e -> {
            String t=addrTitle.getText().trim();
            String a=addrField.getText().trim();
            if(t.isEmpty()||a.isEmpty()){
                JOptionPane.showMessageDialog(mainFrame,
                  "Fill title & address","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            addresses.add(new AddressRecord(t,a));
            updateAddressCombo();
            addrTitle.setText(""); addrField.setText("");
        });

        addCard.addActionListener(e -> {
            String t=ct.getText().trim(),
                   num=cn.getText().trim(),
                   ee=ex.getText().trim(),
                   vv=cv.getText().trim();
            if(t.isEmpty()||num.isEmpty()||ee.isEmpty()||vv.isEmpty()){
                JOptionPane.showMessageDialog(mainFrame,
                  "Fill all card fields","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            paymentMethods.add(new PaymentMethod(t,num,ee,vv));
            updateCardCombo();
            ct.setText(""); cn.setText(""); ex.setText(""); cv.setText("");
        });
    }

    // Helpers
        // Helpers
    private void refreshPaymentItems(){
        paymentItemsModel.clear();
        for(CartItem ci: cart.getItems()){
            paymentItemsModel.addElement(
                ci.getProduct().getName()+
                " x"+ci.getQuantity()+
                " - $"+String.format("%.2f",ci.getTotalPrice())
            );
        }
    }

    private void updateAddressCombo(){
        DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
        for(AddressRecord ar: addresses) {
            m.addElement(ar.title);
        }
        addressCombo.setModel(m);
    }

    private void updateCardCombo(){
        DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
        for(PaymentMethod pm: paymentMethods) {
            m.addElement(pm.title);
        }
        cardCombo.setModel(m);
    }

    // Custom renderer for the orders table
    private static class StatusCellRenderer extends DefaultTableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            boolean done = (Boolean)value;
            String text = done ? table.getColumnName(column) : "";
            super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
            if(column >= 2){
                setBackground(done ? new Color(144,238,144) : new Color(250,128,114));
                setHorizontalAlignment(CENTER);
            }
            return this;
        }
    }

    // Simple data holders
    private static class AddressRecord {
        String title, address;
        AddressRecord(String t, String a){
            title = t;
            address = a;
        }
    }

    private static class PaymentMethod {
        String title, cardNumber, expiry, cvv;
        PaymentMethod(String t, String c, String e, String v){
            title = t;
            cardNumber = c;
            expiry = e;
            cvv = v;
        }
    }

    private static class OrderRecord {
        String orderNo, items;
        OrderRecord(String o, String i){
            orderNo = o;
            items = i;
        }
    }
}
