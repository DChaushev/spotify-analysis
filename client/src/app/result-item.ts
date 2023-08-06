import { Track } from "./track";

export interface ResultItem {
    resultItems: Track[],
    total: number,
    previous: string,
    next: string
}
