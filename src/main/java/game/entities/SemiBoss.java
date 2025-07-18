package game.entities;

import game.graphics.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public  class SemiBoss extends Boss{
    //Atributos

    //Animaciones
    private boolean facingRight = false;

    private int animationIndex = 0;
    private int animationCounter = 0;
    private int animationSpeed = 60;
    private BufferedImage[] walkRightSprites;
    private BufferedImage[] walkLeftSprites;
    private BufferedImage[] exploteSprites;


    //agregado

    private boolean playingDeathAnimation = false;
    private int deathAnimationIndex = 0;
    private int deathAnimationCounter = 0;
    private final int deathAnimationSpeed = 50; // menor = más rápido
    private boolean fullyDead = false; // para eliminarlo si quieres después

    //Constructor
    public SemiBoss(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, health, damage, height, width, y, targetPlayer);
        loadSprites();
    }
    //Update por frames
    @Override
    public void update() {

        if (fullyDead) return; // Ya fue eliminado completamente



        if (!isAlive()) {
            // Si está muerto pero aún no terminó su animación de muerte
            if (!playingDeathAnimation) {
                playingDeathAnimation = true;
                deathAnimationIndex = 0;
                deathAnimationCounter = 0;
            } else {
                // Actualiza la animación de muerte
                deathAnimationCounter++;
                if (deathAnimationCounter >= deathAnimationSpeed) {
                    deathAnimationCounter = 0;
                    deathAnimationIndex++;

                    if (deathAnimationIndex >= exploteSprites.length) {
                        fullyDead = true; // Aquí puedes eliminarlo de la lista si usas un WaveManager
                    }
                }
            }

            return; // No actualizamos movimiento si está muerto
        }

        // Lógica normal si está vivo
        if (targetPlayer == null) return;

        if (x < targetPlayer.x) {
            x += speed;
            facingRight = true;
        } else if (x > targetPlayer.x) {
            x -= speed;
            facingRight = false;
        }

        animationCounter++;
        if (animationCounter >= animationSpeed) {
            animationIndex = (animationIndex + 1) % walkRightSprites.length;
            animationCounter = 0;
        }

        if (isDamaged) {
            damageCooldown--;
            if (damageCooldown <= 0) {
                isDamaged = false;
            }
        }

        if (flashing) {
            flashTimer--;
            if (flashTimer <= 0) {
                flashing = false;
            }
        }

    }

    //Colliders

    @Override
    public Rectangle getBounds() {
        if (!isAlive() || playingDeathAnimation || fullyDead) {
            return new Rectangle(0, 0, 0, 0); // Sin colisión mientras muere
        }

        int colliderWidth = (int) (width * 0.9);
        int colliderHeight = (int) (height * 0.8);
        int offsetX = (int) ((width - colliderWidth) / 2);
        int offsetY = (int) ((height - colliderHeight) / 2);

        return new Rectangle((int) x + offsetX, (int) y + offsetY, colliderWidth, colliderHeight);
    }

    @Override
    public Rectangle getAttackBounds() {
        return getBounds();
    }

    public boolean isFullyDead() {
        return fullyDead;
    }
    @Override
    public boolean shouldBeRemoved() {
        return fullyDead; // solo se elimina después de la animación
    }

    //Sprites y dibujado
    @Override
    public void draw(Graphics g) {
        // Mostrar colisionador
        g.setColor(Color.BLUE);
        Rectangle bounds = getBounds();
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        if (fullyDead) return; // Ya no se dibuja si está completamente eliminado

        // Si está reproduciendo animación de muerte
        if (playingDeathAnimation && deathAnimationIndex < exploteSprites.length) {
            g.drawImage(exploteSprites[deathAnimationIndex], (int) x, (int) y, (int) width, (int) height, null);
            return;
        }

        // === ANIMACIÓN NORMAL (con efecto de parpadeo blanco si fue golpeado) ===
        BufferedImage baseFrame = facingRight
                ? walkRightSprites[animationIndex]
                : walkLeftSprites[animationIndex];

        BufferedImage currentFrame = flashing
                ? makeWhiteImage(baseFrame)
                : baseFrame;

        g.drawImage(currentFrame, (int) x, (int) y, (int) width, (int) height, null);
    }

    private BufferedImage makeWhiteImage(BufferedImage original) {
        BufferedImage whiteImage = new BufferedImage(
                original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = whiteImage.createGraphics();

        g2.drawImage(original, 0, 0, null);
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.setColor(new Color(255, 255, 255, 180)); // Blanco semitransparente
        g2.fillRect(0, 0, original.getWidth(), original.getHeight());
        g2.dispose();

        return whiteImage;
    }

    private void loadSprites() {
        walkLeftSprites = new BufferedImage[4];
        walkRightSprites = new BufferedImage[4];
        exploteSprites = new BufferedImage[8];
        //Sprites movimiento
        walkRightSprites[0] = SpriteLoader.loadImage("/semiBoss/walk0.png");
        walkRightSprites[1] = SpriteLoader.loadImage("/semiBoss/walk1.png");
        walkRightSprites[2] = SpriteLoader.loadImage("/semiBoss/walk2.png");
        walkRightSprites[3] = SpriteLoader.loadImage("/semiBoss/walk3.png");

        walkLeftSprites[0] = SpriteLoader.loadImage("/semiBoss/walk0l.png");
        walkLeftSprites[1] = SpriteLoader.loadImage("/semiBoss/walk1l.png");
        walkLeftSprites[2] = SpriteLoader.loadImage("/semiBoss/walk2l.png");
        walkLeftSprites[3] = SpriteLoader.loadImage("/semiBoss/walk3l.png");
        //Sprites derrota
        exploteSprites [0] = SpriteLoader.loadImage("/semiBoss/plop0.png");
        exploteSprites [1] = SpriteLoader.loadImage("/semiBoss/plop1.png");
        exploteSprites [2] = SpriteLoader.loadImage("/semiBoss/plop2.png");
        exploteSprites [3] = SpriteLoader.loadImage("/semiBoss/plop3.png");
        exploteSprites [4] = SpriteLoader.loadImage("/semiBoss/plop4.png");
        exploteSprites [5] = SpriteLoader.loadImage("/semiBoss/plop5.png");
        exploteSprites [6] = SpriteLoader.loadImage("/semiBoss/plop6.png");
        exploteSprites [7] = SpriteLoader.loadImage("/semiBoss/plop7.png");
    }
}
