from pathlib import Path
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np


PLOTS_DIR = Path("./plots")

ASYMPTOTE_THRESHOLD = 1000
Y_MIN = -10
Y_MAX = 10


def list_csv_files():
    return sorted(PLOTS_DIR.glob("*.csv"))


def load_data(csv_path: Path):
    df = pd.read_csv(csv_path)

    df.columns = [col.strip() for col in df.columns]

    df["x"] = pd.to_numeric(df["x"].astype(str).str.strip(), errors="coerce")
    df["f(x)"] = pd.to_numeric(df["f(x)"].astype(str).str.strip(), errors="coerce")

    df.loc[df["f(x)"].abs() > ASYMPTOTE_THRESHOLD, "f(x)"] = np.nan

    return df["x"], df["f(x)"]


def plot_group(files, title, save_name):
    plt.figure(figsize=(14, 8))

    for file in files:
        x, y = load_data(file)
        plt.plot(x, y, label=file.stem)

    plt.title(title)
    plt.xlim(-2 * np.pi, 2 * np.pi)
    plt.xlabel("x")
    plt.ylabel("f(x)")
    plt.ylim(Y_MIN, Y_MAX)
    plt.grid()
    plt.legend()
    plt.tight_layout()

    plt.savefig(PLOTS_DIR / save_name, dpi=300)
    plt.show()


def main():
    if not PLOTS_DIR.exists():
        print("Folder 'plots' not found")
        return

    files = list_csv_files()

    if not files:
        print("No CSV files found")
        return

    base_files = [f for f in files if f.stem != "EquationSystem"]
    system_files = [f for f in files if f.stem == "EquationSystem"]

    plot_group(
        base_files,
        "Базовые функции",
        "base_functions.png"
    )

    plot_group(
        system_files,
        "Система функций",
        "equation_system.png"
    )


if __name__ == "__main__":
    main()