import { ResultItem } from "./result-item";

export interface ResponseItem {
    statusCode: number,
    reason: string,
    resultItem: ResultItem
}
