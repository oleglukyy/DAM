
import time
import subprocess
from pathlib import Path
from datetime import datetime

from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler

PROJECT = Path(__file__).resolve().parent
WAIT_SECONDS = 300  # 5 minutos

IGNORED = {
    ".git", ".idea", ".gradle", ".venv",
    "venv", "node_modules", "build",
    "dist", "__pycache__"
}


def git(*args):
    return subprocess.run(
        ["git", *args],
        cwd=PROJECT,
        check=True
    )


def add_commit_push():
    try:
        # 1. Añadir cambios
        git("add", "-A")

        # 2. Comprobar si existen cambios
        result = subprocess.run(
            ["git", "diff", "--cached", "--quiet"],
            cwd=PROJECT
        )

        if result.returncode == 0:
            print("No hay cambios para guardar")
            return

        if result.returncode != 1:
            print("Error al comprobar los cambios")
            return

        # 3. Crear commit
        message = datetime.now().strftime(
            "Auto commit: %Y-%m-%d %H:%M:%S"
        )

        git("commit", "-m", message)
        print(f"Commit creado: {message}")

        # 4. Subir a GitHub
        git("push")
        print("Push realizado correctamente")

    except subprocess.CalledProcessError as error:
        print(f"Error de Git: {error}")


class ChangeHandler(FileSystemEventHandler):

    def __init__(self):
        self.last_change = None

    def on_any_event(self, event):
        if event.event_type not in {
            "created", "modified", "deleted", "moved"
        }:
            return

        paths = [event.src_path]

        if event.event_type == "moved":
            paths.append(event.dest_path)

        for path in paths:
            relative = Path(path).relative_to(PROJECT)

            if any(
                part.lower() in IGNORED
                for part in relative.parts
            ):
                continue

            self.last_change = time.monotonic()
            print("Cambio detectado. Contador reiniciado.")
            return


if __name__ == "__main__":
    git("rev-parse", "--show-toplevel")

    handler = ChangeHandler()
    observer = Observer()
    observer.schedule(
        handler, str(PROJECT), recursive=True
    )
    observer.start()

    print(f"Vigilando: {PROJECT}")
    print("Esperando 5 minutos sin modificaciones...")

    try:
        while True:
            time.sleep(1)

            if handler.last_change is None:
                continue

            elapsed = time.monotonic() - handler.last_change

            if elapsed >= WAIT_SECONDS:
                handler.last_change = None
                add_commit_push()

    except KeyboardInterrupt:
        observer.stop()

    observer.join()
