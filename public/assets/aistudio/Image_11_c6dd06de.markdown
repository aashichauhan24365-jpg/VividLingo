---
name: Vibrant Gamified Language
colors:
  surface: '#f8f9ff'
  surface-dim: '#cbdbf5'
  surface-bright: '#f8f9ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#eff4ff'
  surface-container: '#e5eeff'
  surface-container-high: '#dce9ff'
  surface-container-highest: '#d3e4fe'
  on-surface: '#0b1c30'
  on-surface-variant: '#3c4a42'
  inverse-surface: '#213145'
  inverse-on-surface: '#eaf1ff'
  outline: '#6c7a71'
  outline-variant: '#bbcabf'
  surface-tint: '#006c49'
  primary: '#006c49'
  on-primary: '#ffffff'
  primary-container: '#10b981'
  on-primary-container: '#00422b'
  inverse-primary: '#4edea3'
  secondary: '#855300'
  on-secondary: '#ffffff'
  secondary-container: '#fea619'
  on-secondary-container: '#684000'
  tertiary: '#00687a'
  on-tertiary: '#ffffff'
  tertiary-container: '#00b2d0'
  on-tertiary-container: '#003f4b'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#6ffbbe'
  primary-fixed-dim: '#4edea3'
  on-primary-fixed: '#002113'
  on-primary-fixed-variant: '#005236'
  secondary-fixed: '#ffddb8'
  secondary-fixed-dim: '#ffb95f'
  on-secondary-fixed: '#2a1700'
  on-secondary-fixed-variant: '#653e00'
  tertiary-fixed: '#acedff'
  tertiary-fixed-dim: '#4cd7f6'
  on-tertiary-fixed: '#001f26'
  on-tertiary-fixed-variant: '#004e5c'
  background: '#f8f9ff'
  on-background: '#0b1c30'
  surface-variant: '#d3e4fe'
typography:
  headline-xl:
    fontFamily: Nunito Sans
    fontSize: 36px
    fontWeight: '800'
    lineHeight: 44px
    letterSpacing: -0.02em
  headline-xl-mobile:
    fontFamily: Nunito Sans
    fontSize: 28px
    fontWeight: '800'
    lineHeight: 36px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Nunito Sans
    fontSize: 28px
    fontWeight: '800'
    lineHeight: 36px
    letterSpacing: -0.01em
  headline-lg-mobile:
    fontFamily: Nunito Sans
    fontSize: 24px
    fontWeight: '800'
    lineHeight: 32px
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Nunito Sans
    fontSize: 20px
    fontWeight: '700'
    lineHeight: 28px
  body-lg:
    fontFamily: Nunito Sans
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 26px
  body-md:
    fontFamily: Nunito Sans
    fontSize: 16px
    fontWeight: '500'
    lineHeight: 24px
  body-sm:
    fontFamily: Nunito Sans
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
  label-lg:
    fontFamily: Nunito Sans
    fontSize: 16px
    fontWeight: '700'
    lineHeight: 20px
    letterSpacing: 0.02em
  label-md:
    fontFamily: Nunito Sans
    fontSize: 13px
    fontWeight: '700'
    lineHeight: 16px
    letterSpacing: 0.04em
  label-sm:
    fontFamily: Nunito Sans
    fontSize: 11px
    fontWeight: '800'
    lineHeight: 14px
    letterSpacing: 0.06em
rounded:
  sm: 0.5rem
  DEFAULT: 1rem
  md: 1.5rem
  lg: 2rem
  xl: 3rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-tablet: 1.5rem
  gutter-desktop: 2rem
  margin: 1rem
  margin-tablet: 2rem
  margin-desktop: 3rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

## Brand & Style

This design system expresses cheerful momentum, academic rigor wrapped in playful tactile feedback, and friendly encouragement. Designed for daily active language learners spanning beginners to advanced polyglots, the UI transforms cognitive repetition into satisfying, low-friction interactions.

The visual style blends modern tactile affordances with clean, structured surfaces:
- **Playful Tactility:** Interactive elements feature extruded bottom shadows (3D layered offset lips) simulating physical push buttons that depress on click or tap.
- **Friendly Geometry:** Soft pill shapes, generous border radii, and plump iconography convey an approachable, non-intimidating learning environment.
- **High-Clarity Surfaces:** Bright white instructional cards sit atop soft cool-tinted neutral backgrounds to maintain intense contrast and legibility without feeling clinical.

## Colors

The color palette prioritizes high positive reinforcement, energetic feedback, and distinct functional roles:

- **Primary (`#10B981` Emerald Green):** The core active brand color representing success, advancement, correct answers, and progress streaks. The corresponding tactile press base is a darkened tone (`#059669`).
- **Secondary (`#F59E0B` Warm Amber):** The streak, rewards, and XP currency accent. Communicates warmth, excitement, and energy. Its tactile base is `#D97706`.
- **Tertiary (`#06B6D4` Bright Cyan):** Reserved for exploratory paths, grammar tips, and supplementary milestones.
- **Neutrals & Surfaces:**
  - Base background canvas: `#F8FAFC` (cool slate white).
  - Elevated card surfaces: `#FFFFFF` (pure white).
  - Inactive/Border gray: `#E2E8F0` (light border stroke) with extruded base `#CBD5E1`.
  - Body text: `#0F172A` (deep slate) ensuring AAA contrast across all light surfaces.
  - Subdued labels: `#64748B` (medium slate).
- **Error / Correction:** `#EF4444` (ruby red) with extruded base `#DC2626` for incorrect responses.

## Typography

The type system is powered entirely by **Nunito Sans**, utilizing its gentle terminal curves to balance structural legibility with warmth.

- **Headlines (`800` Extrabold):** Used for unit titles, celebration banners, and achievement screens. Tight letter spacing (`-0.02em`) creates punchy, comic-book-inspired titles without sacrificing clarity.
- **Prompts & Question Stems (`700` Bold / `600` SemiBold):** High-weight body levels guarantee instant visual scanning when prompts change between translation, multiple choice, and listening drills.
- **Labels & Micro-copy (`700` - `800` Uppercase / Small):** Used for badges, streak counts, and progress trackers. Slight positive tracking enhances readability on smaller font sizes.

## Layout & Spacing

The layout is built primarily around a touch-first, fluid mobile column structure constrained on larger screens:

- **Mobile (<640px):** Single-column layout with `1rem` outer margins. Action areas (e.g., "Check", "Continue") lock to the bottom safe area with constant `1rem` padding.
- **Tablet (640px–1024px):** 6-column grid with `1.5rem` gutters and centered learning canvas with max-width `540px` to maintain focused thumb-reach zones.
- **Desktop (>1024px):** 12-column grid. The main lesson container stays constrained (`600px` max-width), while auxiliary metadata (learning path sidebar, streak panel) pins to secondary flanking sidebars.
- **Vertical Spacing Rhythm:** Spacing between drill elements relies on `space-md` (16px) for standard item grouping and `space-xl` (32px) to clearly separate prompts from user response zones.

## Elevation & Depth

This system intentionally rejects hazy blur-heavy drop shadows in favor of crisp, structural, tactile elevation:

- **Extruded 3D Lip (Primary Interactive Depth):** Tactile components (buttons, answer tiles, lesson nodes) use a solid, flat bottom border/box-shadow offset without blur.
  - Normal state: `box-shadow: 0 4px 0 0 [Darkened Shading Color]`.
  - Active / Pressed state: `transform: translateY(4px); box-shadow: 0 0 0 0 transparent;`.
- **Card Surfaces:** Crisp white containers float over the `#F8FAFC` slate canvas with a subtle, non-blurry `2px` border (`#E2E8F0`) and an ambient, low-profile resting offset: `box-shadow: 0 2px 0 0 #E2E8F0`.
- **Modal Dialogs & Sheets:** Centered overlays and bottom sheets rest on a dimming overlay (`rgba(15, 23, 42, 0.4)`), surrounded by a solid border with no directional blur.

## Shapes

The design uses a pill-shaped and deeply rounded contour language:

- **Buttons & Chips:** Full pill contour (`rounded-full` or `1rem` - `2rem`) to maximize tactile touch-friendly affordances.
- **Cards & Question Blocks:** `rounded-2xl` (`1.5rem`) on mobile and `rounded-3xl` (`2rem`) on larger screens, delivering an inviting, toy-like software appearance.
- **Progress Trackers:** Fully rounded pill capsules containing sliding fills with matching rounded caps.
- **Answer Selection Tiles:** Generous `1.25rem` corners with a balanced stroke weight (`2px`) ensuring a soft, approachable silhouette.

## Components

### Buttons
- **Tactile Primary Button:** Height 48px–56px. Background `#10B981`, white text, border radius full pill, flat bottom shadow `0 4px 0 0 #059669`. On press: translates down 4px with zero shadow.
- **Secondary / Outline Button:** Background `#FFFFFF`, text `#64748B`, 2px solid border `#E2E8F0`, flat bottom shadow `0 4px 0 0 #CBD5E1`. On select/press, transforms down 4px.
- **Streak / Action Button:** Amber `#F59E0B` with shadow `0 4px 0 0 #D97706`.

### Cards & Exercise Tiles
- Pure white container with 2px stroke in `#E2E8F0`.
- Rounded corners set to `1.5rem`.
- Padding set to `1.25rem` (`20px`).
- Selected state turns the stroke to 2px solid `#10B981`, applies a tinted background `#ECFDF5`, and darkens text.

### Selection Chips & Token Banks
- Pill-shaped draggable or tap-to-select word tokens.
- Light neutral fill (`#FFFFFF`), 2px border `#E2E8F0`, with `0 2px 0 0 #CBD5E1` tactile base.
- Once placed, empty slot placeholders display a dashed border (`#CBD5E1`) and `#F1F5F9` background.

### Lists & Learning Paths
- Vertical serpentine path made of circular lesson nodes (diameter 64px) with custom 3D offset lips.
- Completed nodes show `#10B981` with a gold star badge; active nodes pulse with an animated ring; locked nodes sit in neutral slate (`#E2E8F0`).

### Checkboxes & Radio Buttons
- Custom 24px circular or rounded-lg selectors with 2px solid outlines.
- Selected state fills with primary emerald and features a bold white checkmark/dot.

### Input Fields
- Heavy 2px solid border `#E2E8F0`, corner radius `1rem`, background `#FFFFFF`.
- Focus state switches border to `#10B981` without fuzzy halos, maintaining clean, snappy definition.

### Feedback Banner (Sheet Drawer)
- Slides up from screen bottom on answer evaluation.
- Success: Light emerald background (`#D1FAE5`), dark emerald copy (`#065F46`), emerald tactile button.
- Error: Light red background (`#FEE2E2`), dark red copy (`#991B1B`), ruby red tactile button.