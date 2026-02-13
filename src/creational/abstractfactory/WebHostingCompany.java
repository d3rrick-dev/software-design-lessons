
void main(){
    var factory = new WindowsHostingFactory();
    HostingPackage pkg = factory.createPremium();
    pkg.showFeatures();
}

interface HostingPackage {
    void showFeatures();
}

interface HostingFactory {
    HostingPackage createBasic();
    HostingPackage createPremium();
    HostingPackage createPremiumPlus();
}

static class WindowsHostingFactory implements HostingFactory {

    public HostingPackage createBasic() {
        return new WindowsBasic();
    }

    public HostingPackage createPremium() {
        return new WindowsPremium();
    }

    public HostingPackage createPremiumPlus() {
        return new WindowsPremiumPlus();
    }
}

// Unix-specific packages
static class UnixHostingFactory implements HostingFactory {

    public HostingPackage createBasic() {
        return new UnixBasic();
    }

    public HostingPackage createPremium() {
        return new UnixPremium();
    }

    public HostingPackage createPremiumPlus() {
        return new UnixPremiumPlus();
    }
}

static class WindowsBasic implements HostingPackage {
    public void showFeatures() {
        System.out.println("Windows Basic: 1 GB disk, 1 website, email support");
    }
}

static class WindowsPremium implements HostingPackage {
    public void showFeatures() {
        System.out.println("Windows Premium: 10 GB disk, 5 websites, priority support");
    }
}

static class WindowsPremiumPlus implements HostingPackage {
    public void showFeatures() {
        System.out.println("Windows Premium Plus: Unlimited disk, unlimited websites, 24/7 support");
    }
}

static class UnixBasic implements HostingPackage {
    public void showFeatures() {
        System.out.println("Unix Basic: 1 GB disk, 1 website, email support");
    }
}

static class UnixPremium implements HostingPackage {
    public void showFeatures() {
        System.out.println("Unix Premium: 10 GB disk, 5 websites, priority support");
    }
}

static class UnixPremiumPlus implements HostingPackage {
    public void showFeatures() {
        System.out.println("Unix Premium Plus: Unlimited disk, unlimited websites, 24/7 support");
    }
}
