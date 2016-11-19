package ravioli.gravioli.rpg.player;

public class Experience {
    private RPGPlayer player;

    private long minExperience;
    private long maxExperience;
    private long currentExperience;

    public Experience(RPGPlayer player) {
        this.player = player;

        this.minExperience = this.calculateExperience(player.getLevel());
        this.maxExperience = this.calculateExperience(player.getLevel() + 1);
        this.currentExperience = this.minExperience;
    }

    public void updateExperience() {
        if (this.currentExperience >= this.maxExperience) {
            this.minExperience = this.maxExperience;
            this.player.setLevel(this.player.getLevel() + 1);
            this.player.onLevelUp();
        } else if (this.currentExperience < this.minExperience) {
            this.currentExperience = this.minExperience;
        }
        this.maxExperience = this.calculateExperience(this.player.getLevel());

        float exp = this.currentExperience - this.minExperience;
        float max = this.maxExperience - this.minExperience;

        this.player.getPlayer().setExp(exp / max);
        this.player.getPlayer().setLevel(this.player.getLevel());
    }

    public void addExperience(long experience) {
        this.currentExperience += experience;
        this.updateExperience();
    }

    private long calculateExperience(int level) {
        long total = 0;
        for (int i = 1; i < level; i++) {
            total += (long) Math.floor(i + 300 * Math.pow(2, i / 7.0));
        }
        return (long) Math.floor(total / 4.0);
    }
}
